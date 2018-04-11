package com.zlwon.api.controller;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.exhibition.SearchSpecifyExhibitionDto;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.Exhibition;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.ApplicationCaseService;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.ExhibitionService;
import com.zlwon.server.service.RedisService;
import com.zlwon.vo.applicationCase.ApplicationCaseSimpleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 展会api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/exhibition")
public class ExhibitionApi extends BaseApi {

	@Autowired
	private ExhibitionService exhibitionService;
	
	@Autowired
	private ApplicationCaseService applicationCaseService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 查询特定展会案例分页
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "查询特定展会案例分页")
    @RequestMapping(value = "/searchSpecifyExhibitionCase", method = RequestMethod.POST)
    public ResultPage searchSpecifyExhibitionCase(SearchSpecifyExhibitionDto form){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer exhibitionId = form.getExhibitionId();  //展会ID
		String caseName = form.getCaseName();  //应用产品（模糊）
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		String entryKey = form.getEntryKey();  //微信加密字符串
		Integer manufacturerId = form.getManufacturerId();  //生产商
		
		if(exhibitionId == null || currentPage == null || pageSize == null || StringUtils.isBlank(entryKey)){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		/*String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultPage.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}*/
		
		//根据展会ID查询展会信息
		Exhibition myEx = exhibitionService.findExhibitionInfoById(exhibitionId);
		if(myEx == null){
			return ResultPage.error(StatusCode.DATA_NOT_EXIST);
		}
	
		//分页查询特定展会的案例简单详情
		PageInfo<ApplicationCaseSimpleVo> pageList = applicationCaseService.selectSpecifyExhibitionCase(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * 根据展会ID查询该展会供应商
	 * @param exhibitionId
	 * @return
	 */
	@ApiOperation(value = "根据展会ID查询该展会供应商")
    @RequestMapping(value = "/queryManufacturerById/{exhibitionId}/{entryKey}", method = RequestMethod.GET)
    public ResultData queryManufacturerById(@PathVariable Integer exhibitionId,@PathVariable String entryKey){
		
		//验证参数
		if(exhibitionId == null || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		/*String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}*/
		
		//根据展会ID查询该展会供应商
		List<Customer> list = customerService.selectManufacturerByExId(exhibitionId);
		
		return ResultData.one(list);
	}
}

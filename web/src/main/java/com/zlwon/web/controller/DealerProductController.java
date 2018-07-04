package com.zlwon.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.web.dealerProductMap.InsertDealerProductMapBatchWebDto;
import com.zlwon.dto.web.dealerProductMap.InsertDealerProductMapDto;
import com.zlwon.dto.web.dealerProductMap.QueryDealerProductMapByUidPageDto;
import com.zlwon.dto.web.dealerProductMap.UpdateDealerProductMapDto;
import com.zlwon.rdb.entity.DealerProductMap;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.DealerProductMapService;
import com.zlwon.vo.web.dealerdQuotation.DealerProductMapSimpleVo;
import com.zlwon.web.annotations.AuthLogin;

/**
 * 经销商产品接口
 * @author yangy
 *
 */

@AuthLogin
@RestController
@RequestMapping("dealerProduct")
public class DealerProductController {

	@Autowired
	private DealerProductMapService dealerProductMapService;
	
	/**
	 * web端分页查询经销商可供产品
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "queryDealerProductMapByUidPage", method = RequestMethod.POST)
	public ResultPage queryDealerProductMapByUidPage(QueryDealerProductMapByUidPageDto form){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数
		Integer userId = form.getUserId();  //用户ID

		if(currentPage == null || pageSize == null || userId == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//分页查询
		PageInfo<DealerProductMapSimpleVo> pageList = dealerProductMapService.findDealerProductMapByUidPage(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * 根据ID删除经销商可供商品记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteDealerProductMapById", method = RequestMethod.GET)
	public ResultData deleteDealerProductMapById(Integer id){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		int count = dealerProductMapService.deleteDealerProductMapById(id);
		
		return ResultData.ok();
	}
	
	/**
	 * 编辑修改经销商可供商品记录
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "updateDealerProductMap", method = RequestMethod.POST)
	public ResultData updateDealerProductMap(UpdateDealerProductMapDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer id = form.getId();  //ID
		String availableIndustry = form.getAvailableIndustry();  //可供行业
		String availableArea = form.getAvailableArea();  //经销商可供产品表
		
		if(id == null || StringUtils.isBlank(availableIndustry) || StringUtils.isBlank(availableArea)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		DealerProductMap record = new DealerProductMap();
		record.setId(id);
		record.setAvailableIndustry(availableIndustry);
		record.setAvailableArea(availableArea);
		
		int count = dealerProductMapService.updateDealerProductMap(record);
		
		return ResultData.ok();
	}
	
	/**
	 * 根据ID查询经销商可供商品记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryDealerProductMapById", method = RequestMethod.GET)
	public ResultData queryDealerProductMapById(Integer id){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		DealerProductMap result = dealerProductMapService.findDealerProductMapById(id);
		
		return ResultData.one(result);
	}
	
	/**
	 * 批量新增经销商可供产品记录(系统管理员)
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "insertDealerProductMapBatch", method = RequestMethod.POST)
	@ResponseBody
	public ResultData insertDealerProductMapBatch(@RequestBody InsertDealerProductMapBatchWebDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		List<InsertDealerProductMapDto> formList = form.getFormList();  //批量新增经销商可供产品List
		Integer userId = form.getUserId();  //用户ID
		
		if(formList == null || formList.size() == 0 || userId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//批量插入
		int count = dealerProductMapService.insertBatchDealerProductMap(formList,userId);
		
		return ResultData.ok();
	}
}

package com.zlwon.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.web.dealerdQuotation.ExamineDealerdQuotationDto;
import com.zlwon.dto.web.dealerdQuotation.QueryAllDealerdQuotationPageDto;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.DealerdQuotationService;
import com.zlwon.vo.applicationCaseEdit.ApplicationCaseEditListVo;
import com.zlwon.vo.pc.dealerQuotate.DealerdQuotationDetailVo;
import com.zlwon.web.annotations.AuthLogin;

/**
 * 材料报价单
 * @author yangy
 *
 */

@AuthLogin
@RestController
@RequestMapping("dealerdQuotation")
public class DealerdQuotationController {

	@Autowired
	private DealerdQuotationService dealerdQuotationService;
	
	/**
	 * 分页查询材料报价单
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "queryMyDealerdQuotationPage", method = RequestMethod.POST)
	public ResultPage queryAllDealerdQuotationPage(QueryAllDealerdQuotationPageDto form){
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		//分页查询材料报价单
		PageInfo<DealerdQuotationDetailVo> pageList = dealerdQuotationService.findAllDealerdQuotationPage(form);
		
		return ResultPage.list(pageList);
	}
	
	/**
	 * 根据ID删除材料报价单
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteDealerdQuotationById", method = RequestMethod.GET)
	public ResultData deleteDealerdQuotationById(Integer id){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		int count = dealerdQuotationService.deleteDealerdQuotationById(id);
		
		return ResultData.ok();
	}
	
	/**
	 * 根据ID查询材料报价单信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryDealerdQuotationById", method = RequestMethod.GET)
	public ResultData queryDealerdQuotationById(Integer id){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		DealerdQuotationDetailVo result = dealerdQuotationService.findDealerdQuotationDetailById(id);
		
		return ResultData.one(result);
	}
	
	/**
	 * 审核材料报价单
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "examineDealerdQuotation", method = RequestMethod.POST)
	public ResultData examineDealerdQuotation(ExamineDealerdQuotationDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer id = form.getId();  //材料报价单ID
		Integer examine = form.getExamine();  //审核状态 1：通过 2：驳回
		String reason = form.getReason();  //审核理由
		
		if(id == null || examine == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		if(examine == 2 && StringUtils.isBlank(reason)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		int count = dealerdQuotationService.examineDealerdQuotation(form);
		
		return ResultData.ok();
	}
	
	/**
	 * 批量审核通过材料报价单
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "examineDealerdQuotationBatch", method = RequestMethod.POST)
	public  ResultData examineDealerdQuotationBatch(Integer[] ids){
		
		//验证参数
		if(ids == null && ids.length < 1){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		ExamineDealerdQuotationDto form = null;
		
		for (Integer id : ids) {
			form = new  ExamineDealerdQuotationDto();
			form.setExamine(1);
			form.setId(id);
			int count = dealerdQuotationService.examineDealerdQuotation(form);
		}
		
		
		return ResultData.ok();
	}
	
	
	
	/**
	 * web端首页查看未审核的报价单，不分页
	 * @return
	 */
	@RequestMapping(value="queryNotExamineDealerdQuotation",method=RequestMethod.GET)
	public  ResultData  queryNotExamineDealerdQuotation(@RequestParam(defaultValue="${page.pageSize}")Integer  pageSize){
		List<DealerdQuotationDetailVo> list = dealerdQuotationService.findNotExamineDealerdQuotation(pageSize);
		return  ResultData.one(list);
	}
	
	/**
	 * 批量导入材料报价单
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "batchImportDealerdQuotation", method = RequestMethod.POST)
	public ResultData batchImportDealerdQuotation(@RequestParam("file") MultipartFile file){
		
		int count = dealerdQuotationService.importDealerdQuotationById(file);
		
		return ResultData.ok();
	}
}

package com.zlwon.pc.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.dealerdQuotation.InsertDealerdQuotationDto;
import com.zlwon.dto.pc.dealerdQuotation.QueryMyDealerdQuotationPageDto;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.DealerdQuotation;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.DealerdQuotationService;
import com.zlwon.vo.pc.dealerQuotate.DealerdQuotationDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 材料报价单pc端接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/pc/dealerdQuotation")
public class DealerdQuotationController extends BaseController {

	@Autowired
	private DealerdQuotationService dealerdQuotationService;
	
	/**
	 * pc端新增材料报价单
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端新增材料报价单")
    @RequestMapping(value = "/insertDealerdQuotation", method = RequestMethod.POST)
	public ResultData insertDealerdQuotation(InsertDealerdQuotationDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer sid = form.getSid();  //物性表Id
		String color = form.getColor();  //颜色/色号
		Float price = form.getPrice();  //报价
		Date validityDate = form.getValidityDate();  //有效期（截止日期，3个月内）
		Integer orderQuantity = form.getOrderQuantity();  //起订量
		String deliveryDate = form.getDeliveryDate();  //交货期
		String deliveryPlace = form.getDeliveryPlace();  //交货地点
		String payMethod = form.getPayMethod();  //支付方式
		
		if(sid == null || StringUtils.isBlank(color) || price == null || validityDate == null || 
				orderQuantity == null || StringUtils.isBlank(deliveryDate) || StringUtils.isBlank(deliveryPlace) || StringUtils.isBlank(payMethod)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer userId = user.getId();  //用户ID
		
		//新增材料报价单
		DealerdQuotation record = new DealerdQuotation();
		record.setUid(userId);
		record.setSid(sid);
		record.setColor(color);
		record.setPrice(price);
		record.setValidityDate(validityDate);
		record.setOrderQuantity(orderQuantity);
		record.setDeliveryDate(deliveryDate);
		record.setDeliveryPlace(deliveryPlace);
		record.setPayMethod(payMethod);
		record.setExamine(0);
		record.setCreateTime(new Date());
		
		int count = dealerdQuotationService.insertDealerdQuotation(record);
		
		return ResultData.ok();
	}
	
	/**
	 * pc端根据ID删除材料报价单
	 * @param id
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端根据ID删除材料报价单")
    @RequestMapping(value = "/deleteDealerdQuotationById", method = RequestMethod.GET)
	public ResultData deleteDealerdQuotationById(@RequestParam Integer id,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		int count = dealerdQuotationService.deleteDealerdQuotationById(id);
		
		return ResultData.ok();
	}
	
	/**
	 * pc端分页查询我的材料报价单
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端分页查询我的材料报价单")
    @RequestMapping(value = "/queryMyDealerdQuotationPage", method = RequestMethod.POST)
	public ResultPage queryMyDealerdQuotationPage(QueryMyDealerdQuotationPageDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		//获取用户信息
		Customer user = accessCustomerByToken(token);
		if(user == null){
			return ResultPage.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证参数
		if(form == null){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		Integer currentPage = form.getCurrentPage();  //当前页
		Integer pageSize = form.getPageSize();  //每页显示的总条数

		if(currentPage == null || pageSize == null ){
			return ResultPage.error(StatusCode.INVALID_PARAM);
		}
		
		form.setUserId(user.getId());
		
		//分页查询我的材料报价单
		PageInfo<DealerdQuotationDetailVo> pageList = dealerdQuotationService.findDealerdQuotationByUidPage(form);
		
		return ResultPage.list(pageList);
	}
}

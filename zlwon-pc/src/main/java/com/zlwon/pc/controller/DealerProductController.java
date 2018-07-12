package com.zlwon.pc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.dealerProduct.SendDealerProductToSysDto;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.DealerProductMapService;
import com.zlwon.server.service.MailService;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.pc.dealerdQuotation.DealerProductMapDetailVo;
import com.zlwon.vo.specification.SpecificationDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 经销商可供产品pc端接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/pc/dealerProduct")
public class DealerProductController extends BaseController {

	@Autowired
	private DealerProductMapService dealerProductMapService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SpecificationService specificationService;
	
	/**
	 * pc端根据物性ID查询经销商可供产品列表
	 * @param specId
	 * @return
	 */
	@ApiOperation(value = "pc端根据物性ID查询经销商可供产品列表")
    @RequestMapping(value = "/queryDealerProductBySpecId", method = RequestMethod.GET)
    public ResultData queryDealerProductBySpecId(@RequestParam Integer specId){
		
		List<DealerProductMapDetailVo> list = dealerProductMapService.findDealerProductMapBySpecId(specId);
		
		return ResultData.one(list);
	}
	
	/**
	 * 发送经销商邮件
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "发送经销商邮件")
    @RequestMapping(value = "/sendDealerProductToSystem", method = RequestMethod.POST)
	public ResultData sendDealerProductToSystem(SendDealerProductToSysDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		String name = form.getName();  //姓名
		String mail = form.getMail();  //电邮
		String mobile = form.getMobile();  //电话
		String company = form.getCompany();  //公司名称
		String advice = form.getAdvice();  //查询/意见
		Integer specId = form.getSpecId();  //物性规格
		String dealerProduct = form.getDealerProduct();  //经销商
		
		if(StringUtils.isBlank(mail) || StringUtils.isBlank(mobile)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//根据物性表ID查询物性表详情
		SpecificationDetailVo specInfo = specificationService.findSpecDetailById(specId);
		if(specInfo == null){
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		
		//邮件名称
		String title = "关于"+specInfo.getName()+"经销商咨询-"+dealerProduct;
		
		//邮件内容
		StringBuilder content = new StringBuilder();
		content.append("<div>电话："+mobile+"<div>");
		content.append("<br/>");
		content.append("<div>电邮："+mail+"<div>");
		content.append("<br/>");
		
		if(StringUtils.isBlank(name)){
			content.append("<div>姓名：无<div>");
		}else{
			content.append("<div>姓名："+name+"<div>");
		}
		content.append("<br/>");
		
		if(StringUtils.isBlank(company)){
			content.append("<div>公司名称：无<div>");
		}else{
			content.append("<div>公司名称："+company+"<div>");
		}
		content.append("<br/>");
		
		if(StringUtils.isBlank(advice)){
			content.append("<div>查询/意见：无<div>");
		}else{
			content.append("<div>查询/意见："+advice+"<div>");
		}
		content.append("<br/>");
		
		mailService.sendHtmlMail("yg.chen@zlwon.com", title, content.toString());
		
		return ResultData.ok();
	}
}

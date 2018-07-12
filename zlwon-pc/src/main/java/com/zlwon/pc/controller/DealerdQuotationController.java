package com.zlwon.pc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zlwon.constant.IntegrationDeatilCode;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.dealerdQuotation.DealerdQuotationEmailDto;
import com.zlwon.dto.pc.dealerdQuotation.InsertDealerdQuotationDto;
import com.zlwon.dto.pc.dealerdQuotation.QueryMyDealerdQuotationPageDto;
import com.zlwon.dto.pc.dealerdQuotation.UpdateDealerdQuotationDto;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.DealerdQuotation;
import com.zlwon.rdb.entity.IntegrationDeatilMap;
import com.zlwon.rdb.entity.Specification;
import com.zlwon.rest.ResultData;
import com.zlwon.rest.ResultPage;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.DealerdQuotationService;
import com.zlwon.server.service.IntegrationDeatilMapService;
import com.zlwon.server.service.MailService;
import com.zlwon.server.service.SpecificationService;
import com.zlwon.vo.pc.dealerQuotate.DealerdQuotationDetailVo;
import com.zlwon.vo.specification.SpecificationDetailVo;

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
	
	@Autowired
	private SpecificationService specificationService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private IntegrationDeatilMapService integrationDeatilMapService;
	
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
		
		String specName = form.getSpecName();  //物性表规格名
		String color = form.getColor();  //颜色/色号
		Float price = form.getPrice();  //报价
		Date validityDate = form.getValidityDate();  //有效期（截止日期，3个月内）
		Integer orderQuantity = form.getOrderQuantity();  //起订量
		String deliveryDate = form.getDeliveryDate();  //交货期
		String deliveryPlace = form.getDeliveryPlace();  //交货地点
		String payMethod = form.getPayMethod();  //支付方式
		
		if(StringUtils.isBlank(specName) || StringUtils.isBlank(color) || price == null || validityDate == null || 
				orderQuantity == null || StringUtils.isBlank(deliveryDate) || StringUtils.isBlank(deliveryPlace) || StringUtils.isBlank(payMethod)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		Integer userId = user.getId();  //用户ID
		
		//用户不是企业用户
		if(user.getRole() != 6){  
			return ResultData.error(StatusCode.PERMIT_USER_LIMIT);
		}
		
		//查询物性规格
		Specification validSpec = specificationService.findSpecificationByName(specName);
		if(validSpec == null){
			return ResultData.error(StatusCode.SPECIFICATION_NOT_EXIST);
		}
		
		//新增材料报价单
		DealerdQuotation record = new DealerdQuotation();
		record.setUid(userId);
		record.setSid(validSpec.getId());
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
	 * pc端批量新增材料报价单
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端批量新增材料报价单")
    @RequestMapping(value = "/insertDealerdQuotationBatch", method = RequestMethod.POST)
	@ResponseBody
	public ResultData insertDealerdQuotationBatch(@RequestBody List<InsertDealerdQuotationDto> form,HttpServletRequest request){
		
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
		
		Integer userId = user.getId();  //用户ID
		
		//用户不是企业用户
		if(user.getRole() != 6){  
			return ResultData.error(StatusCode.PERMIT_USER_LIMIT);
		}
		
		//批量插入材料报价单
		int count = dealerdQuotationService.insertDealerdQuotationBatch(form,userId);
		
		return ResultData.ok();
	}
	
	/**
	 * pc端编辑材料报价单
	 * @param form
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端编辑材料报价单")
    @RequestMapping(value = "/updateDealerdQuotation", method = RequestMethod.POST)
	public ResultData updateDealerdQuotation(UpdateDealerdQuotationDto form,HttpServletRequest request){
		
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
		
		Integer dealerId = form.getDealerId();  //材料报价单ID
		Float price = form.getPrice();  //报价
		Date validityDate = form.getValidityDate();  //有效期（截止日期，3个月内）
		Integer orderQuantity = form.getOrderQuantity();  //起订量
		String deliveryDate = form.getDeliveryDate();  //交货期
		String deliveryPlace = form.getDeliveryPlace();  //交货地点
		String payMethod = form.getPayMethod();  //支付方式
		
		if(dealerId == null || price == null || validityDate == null || orderQuantity == null || 
				StringUtils.isBlank(deliveryDate) || StringUtils.isBlank(deliveryPlace) || StringUtils.isBlank(payMethod)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//编辑材料报价单
		DealerdQuotation record = new DealerdQuotation();
		record.setId(dealerId);
		record.setPrice(price);
		record.setValidityDate(validityDate);
		record.setOrderQuantity(orderQuantity);
		record.setDeliveryDate(deliveryDate);
		record.setDeliveryPlace(deliveryPlace);
		record.setPayMethod(payMethod);
		record.setExamine(0);
		
		int count = dealerdQuotationService.updateDealerdQuotation(record);
		
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
	 * pc端根据ID查询材料报价单信息
	 * @param id
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "pc端根据ID查询材料报价单信息")
    @RequestMapping(value = "/queryDealerdQuotationById", method = RequestMethod.GET)
	public ResultData queryDealerdQuotationById(@RequestParam Integer id,HttpServletRequest request){
		
		//验证参数
		if(id == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//DealerdQuotation result = dealerdQuotationService.findDealerdQuotationById(id);
		DealerdQuotationDetailVo result = dealerdQuotationService.findDealerdQuotationDetailById(id);
		
		return ResultData.one(result);
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
	
	/**
	 * pc端根据ID发送邀请详谈报价邮件
	 * @param id
	 * @param request
	 * @return
	 */
	@AuthLogin
	@ApiOperation(value = "pc端根据ID发送邀请详谈报价邮件")
    @RequestMapping(value = "/sendInviteQuotationById", method = RequestMethod.GET)
	public ResultData sendInviteQuotationById(@RequestParam Integer id,HttpServletRequest request){
		
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
		
		//验证权限,必须为认证用户
		if(user.getRole() != 1 && user.getRole() != 6){
			return ResultData.error(StatusCode.PERMIT_USER_AUTHENTIC_LIMIT);
		}
		
		//验证用户积分是否足够
		if(user.getIntegration() < Math.abs(IntegrationDeatilCode.CONSULTE_EMAIL_QUOTATION.getNum())){
			return ResultData.error(StatusCode.USER_INTEGRATION_NOT_ENOUGH);
		}
		
		DealerdQuotation result = dealerdQuotationService.findDealerdQuotationById(id);
		if(result == null){
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		
		//查询物性规格
		Specification currentSpec = specificationService.findSpecificationById(result.getSid());
		
		//查询邮件接受者的信息
		Customer inviteUser = customerService.findCustomerById(result.getUid());
		if(StringUtils.isBlank(inviteUser.getEmail())){
			return ResultData.error(StatusCode.MAIL_NOT_EXIST);
		}else{
			Map<String, Object> model = new HashMap<String, Object>();
	        model.put("nickname", inviteUser.getNickname());  //被邀请者昵称
	        model.put("headerimg", inviteUser.getHeaderimg());  //被邀请者头像
	        model.put("quoteNickName", user.getNickname());  //邀请者昵称
	        model.put("source", currentSpec.getName());  //来源
	        model.put("mobile", user.getMobile());  //手机号码
	        model.put("email", user.getEmail());  //邮箱
	        model.put("simpleCompany", user.getCompany());  //公司简称
	        
	        mailService.sendVelocityTemplateMail(inviteUser.getEmail(), "来自"+user.getCompany()+"的"+user.getNickname()+"对您的报价"+currentSpec.getName()+"感兴趣，希望你能尽快联系ta", "invitateQuotaEmail.vm",model);
	        
	        //减少积分
	        int upCount = customerService.updateIntegrationByUid(user.getId(), IntegrationDeatilCode.CONSULTE_EMAIL_QUOTATION.getNum());
			
			//减少积分异动明细
			IntegrationDeatilMap interDeatil = new IntegrationDeatilMap();
			interDeatil.setType(IntegrationDeatilCode.CONSULTE_EMAIL_QUOTATION.getCode());
			interDeatil.setDescription(IntegrationDeatilCode.CONSULTE_EMAIL_QUOTATION.getMessage());
			interDeatil.setIntegrationNum(IntegrationDeatilCode.CONSULTE_EMAIL_QUOTATION.getNum());
			interDeatil.setChangeType(0);
			interDeatil.setUid(user.getId());
			interDeatil.setCreateTime(new Date());
			
			int igCount = integrationDeatilMapService.insertIntegrationDeatilMap(interDeatil);
		}
		
		return ResultData.ok();
	}
	
	/**
	 * 我要询价，发送邮件
	 * @param form
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "我要询价，发送邮件")
    @RequestMapping(value = "/sendUserQutotationEmail", method = RequestMethod.POST)
	public ResultData sendUserQutotationEmail(DealerdQuotationEmailDto form,HttpServletRequest request){
		
		//验证token
		String token = request.getHeader("token");
		
		Integer  specId = form.getSpecId();//物性规格ID
		String color = form.getColor();  //颜色/色号
		Integer count = form.getCount();  //数量
		String  username = form.getUsername();//用户姓名
		String  email = form.getEmail();//用户邮箱
		String  phone = form.getPhone();//用户电话
		String  company = form.getCompany();//用户公司名称
		String  content = form.getContent();//查询/意见内容
		
		if(StringUtils.isBlank(email) || StringUtils.isBlank(phone) || StringUtils.isBlank(color) || count == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//根据物性表ID查询物性表详情
		SpecificationDetailVo specInfo = specificationService.findSpecDetailById(specId);
		if(specInfo == null){
			return ResultData.error(StatusCode.DATA_NOT_EXIST);
		}
		
		//邮件名称
		String title = "关于"+specInfo.getName()+"的询价";
		
		//邮件内容
		StringBuilder text = new StringBuilder();
		text.append("<div>电话："+phone+"<div>");
		text.append("<br/>");
		text.append("<div>电邮："+email+"<div>");
		text.append("<br/>");
		
		if(StringUtils.isBlank(username)){
			text.append("<div>姓名：无<div>");
		}else{
			text.append("<div>姓名："+username+"<div>");
		}
		text.append("<br/>");
		
		if(StringUtils.isBlank(company)){
			text.append("<div>公司名称：无<div>");
		}else{
			text.append("<div>公司名称："+company+"<div>");
		}
		text.append("<br/>");
		
		if(StringUtils.isBlank(content)){
			text.append("<div>备注：无<div>");
		}else{
			text.append("<div>备注："+content+"<div>");
		}
		text.append("<br/>");
		
		mailService.sendHtmlMail("yg.chen@zlwon.com", title , content);
		
		return ResultData.ok();
	}
}

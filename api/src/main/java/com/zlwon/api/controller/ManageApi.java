package com.zlwon.api.controller;

import com.alibaba.fastjson.JSON;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.manage.*;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.MailService;
import com.zlwon.server.service.MobileMessageService;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.EmailFormatCheckUtils;
import com.zlwon.utils.MD5Utils;
import com.zlwon.utils.PhoneFormatCheckUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 系统应用相关管理api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/manage")
public class ManageApi extends BaseApi {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MobileMessageService mobileMessageService;
	
	/**
	 * 所有用户登录
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "所有用户登录")
    @RequestMapping(value = "/loginAllCustomer", method = RequestMethod.POST)
	public ResultData loginAllCustomer(LoginAllDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String loginName = form.getLoginName();  //登录账户
		String password = form.getPassword();  //登录密码
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		if(StringUtils.isBlank(loginName) || StringUtils.isBlank(password) || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//获取登录用户
		Customer user = judgeLoginNameType(loginName,password);
		if(user == null){
			return ResultData.error(StatusCode.PASSWORD_INVALID);
		}
		
		//如果用户openId不存在，则置入openId
		if(StringUtils.isBlank(user.getOpenid())){
			int count = customerService.updateCustomerOpenIdById(openId, user.getId());
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
		}
		
		//将对象转化为json字符串
		String objectJson = JSON.toJSONString(user);
		//存储进redis，命名规则openId_+对应openId字符串
		redisService.set("openId_"+openId, objectJson,60*5,TimeUnit.SECONDS);
		
		return ResultData.one(user);
	}
	
	/**
	 * 知料师用户登录-根据登录名和密码查询有效用户
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "知料师用户登录-根据登录名和密码查询有效用户")
    @RequestMapping(value = "/loginTraditionCustomer", method = RequestMethod.POST)
	public ResultData loginTraditionCustomer(LoginBasicDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String loginName = form.getLoginName();  //登录账户
		String password = form.getPassword();  //登录密码
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		if(StringUtils.isBlank(loginName) || StringUtils.isBlank(password) || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//获取登录用户
		Customer user = judgeLoginNameType(loginName,password);
		if(user == null){
			return ResultData.error(StatusCode.PASSWORD_INVALID);
		}
		
		//判断是否是知料师用户
		if(user.getRole() != 1){  //如果不是知料师用户
			return ResultData.error(StatusCode.USER_NOT_ENGINEER);
		}
		
		//如果用户openId不存在，则置入openId
		if(StringUtils.isBlank(user.getOpenid())){
			int count = customerService.updateCustomerOpenIdById(openId, user.getId());
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
		}
		
		return ResultData.one(user);
	}
	
	/*@ApiOperation(value = "判断微信一般用户是否处于登录状态（openId存在与否）")
    @RequestMapping(value = "/queryLoginStatusByOpenId/{openId}", method = RequestMethod.GET)
	public ResultData queryLoginStatusByOpenId(@PathVariable String openId){
		
		//验证参数
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String jsonStr = "";
		
		//验证openId是否存在于redis中,命名规则openId_+对应openId字符串
		String redisValue = redisService.get("openId_"+openId);
		if(StringUtils.isBlank(redisValue)){  //如果缓存不存在
			//根据openId查询数据库
			Customer user = customerService.selectCustomerByOpenId(openId);
			if(user == null){
				return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
			}else{
				//将对象转化为json字符串
				String objectJson = JSON.toJSONString(user);
				jsonStr = objectJson;
				//存储进redis，命名规则openId_+对应openId字符串
				redisService.set("openId_"+openId, jsonStr,60*5,TimeUnit.SECONDS);
			}
		}else{
			jsonStr = redisValue;
		}
		
		return ResultData.one(jsonStr);
	}*/
	
	/**
	 * 一般用户登录-主要加入openId
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "一般用户登录-主要加入openId")
    @RequestMapping(value = "/loginCommonCustomer", method = RequestMethod.POST)
	public ResultData loginCommonCustomer(LoginWeixinDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String loginName = form.getLoginName();  //登录账户
		String password = form.getPassword();  //登录密码
		String entryKey = form.getEntryKey();  //微信加密字符串
		
		if(StringUtils.isBlank(loginName) || StringUtils.isBlank(password) || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		String jsonStr = "";
		//获取登录用户
		Customer user = judgeLoginNameType(loginName,password);
		
		//判断用户是否存在
		if(user == null){
			return ResultData.error(StatusCode.PASSWORD_INVALID);
		}
		
		//判断用户openId是否存在
		String myOpenId = user.getOpenid();  //获取登录用户的openId
		if(StringUtils.isBlank(myOpenId)){
			user.setOpenid(openId);
			
			//根据用户ID修改openId
			int count = customerService.updateCustomerOpenIdById(openId, user.getId());
			if(count == 0){
				return ResultData.error(StatusCode.SYS_ERROR);
			}
			
		}else{
			if(!myOpenId.equals(openId)){
				return ResultData.error(StatusCode.PASSWORD_INVALID);
			}
		}
		
		//将对象转化为json字符串
		String objectJson = JSON.toJSONString(user);
		jsonStr = objectJson;
		//存储进redis，命名规则openId_+对应openId字符串
		redisService.set("openId_"+openId, jsonStr,60*5,TimeUnit.SECONDS);
		
		return ResultData.one(user);
	}
	
	/**
	 * 根据手机号发送短信验证码
	 * @param mobile
	 * @return
	 */
	@ApiOperation(value = "根据手机号发送短信验证码")
    @RequestMapping(value = "/sendPhoneCode/{mobile}", method = RequestMethod.GET)
	public ResultData sendPhoneCode(@PathVariable String mobile){
		
		//验证参数
		if(StringUtils.isBlank(mobile)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证手机格式-正则验证
		if(!PhoneFormatCheckUtils.isPhoneLegal(mobile)){
			return ResultData.error(StatusCode.MOBILE_FORMAT_ERROR);
		}
		
		//发送短信验证码
		SmsSingleSenderResult result = mobileMessageService.sendCodeMessage(mobile);
		if(result.result != 0){
			return ResultData.error(StatusCode.MESSAGE_SEND_FAIL);
		}
		
		return ResultData.ok();
	}
	
	/**
	 * 手动录入注册
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "手动录入注册")
    @RequestMapping(value = "/registerInputCustomer", method = RequestMethod.POST)
	public ResultData registerInputCustomer(RegisterInputDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String mobile = form.getMobile();  //手机号码
		String mobileCode = form.getMobileCode();  //短信验证码
		//String mail = form.getMail();  //常用邮箱
		String entryKey = form.getEntryKey();  //微信加密字符串
		String nickName = form.getNickName();  //昵称
		String headerimg = form.getHeaderimg();  //用户头像
		
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(mobileCode) || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证手机号码格式
		if(!PhoneFormatCheckUtils.isPhoneLegal(mobile)){
			return ResultData.error(StatusCode.MOBILE_FORMAT_ERROR);
		}
		
		//根据手机号码查询有效用户
		Customer validmobile = customerService.selectCustomerByMobile(mobile);
		if(validmobile != null){
			//return ResultData.error(StatusCode.MOBILE_IS_REGISTER);
			
			//更新openId
			if(StringUtils.isBlank(validmobile.getOpenid())){
				validmobile.setOpenid(openId);
				int count = customerService.updateCustomerOpenIdById(openId, validmobile.getId());
				if(count == 0){
					return ResultData.error(StatusCode.SYS_ERROR);
				}
			}
			
			//将对象转化为json字符串
			String mobileJson = JSON.toJSONString(validmobile);
			//存储进redis，命名规则openId_+对应openId字符串
			redisService.set("openId_"+openId, mobileJson,60*5,TimeUnit.SECONDS);
			
			return ResultData.one(validmobile);
		}
		
		//如果邮箱不为空
		/*if(!StringUtils.isBlank(mail)){
			//验证邮箱格式
			if(!EmailFormatCheckUtils.checkEmail(mail)){
				return ResultData.error(StatusCode.MAIL_FORMAT_ERROR);
			}
			
			//判断邮箱是否被使用
			Customer validmail = customerService.selectCustomerByMail(mail);
			if(validmail != null){
				return ResultData.error(StatusCode.MAIL_IS_REGISTER);
			}
		}*/
				
		//验证短信验证码，从redis取值
		String validcode = redisService.get("mobilecode_"+mobile);
		//String validcode = "123456";
		//验证短信验证码是否过期
		if(StringUtils.isBlank(validcode)){
			return ResultData.error(StatusCode.ACTIVE_CODE_EXPIRED);
		}
		//验证短信验证码是否输入正确
		if(!mobileCode.equals(validcode)){
			return ResultData.error(StatusCode.ACTIVE_CODE_INVALID);
		}
		
		Customer temp = new Customer();
		temp.setRole(0);
		/*if(StringUtils.isBlank(nickName)){
			temp.setNickname("知料网用户");
		}else{
			temp.setNickname(nickName);
		}*/
		if(StringUtils.isNotBlank(nickName)){
			temp.setNickname(nickName);
		}else{
			temp.setNickname(mobile);
		}
		//temp.setEmail(mail);
		temp.setEmail(null);
		temp.setMobile(mobile);
		temp.setPassword(MD5Utils.encode("666666"));
		temp.setCreateTime(new Date());
		temp.setMobileValidate(1);
		temp.setEmailValidate(1);
		temp.setOpenid(openId);
		temp.setName(null);
		temp.setCompany(null);
		temp.setOccupation(null);
		temp.setBcard(null);
		if(StringUtils.isBlank(headerimg)){
			temp.setHeaderimg(null);
		}else{
			temp.setHeaderimg(headerimg);
		}
		temp.setIntegration(0);
		temp.setGold(0);
		temp.setIntro(null);
		temp.setMyinfo(null);
		temp.setLabel(null);
		temp.setApply(0);
		temp.setApplyTime(null);
		temp.setDel(1);
		temp.setRemark(null);
		
		//新增用户
		Customer result = customerService.insertCustomer(temp);
		
		//如果邮箱存在，则发送注册成功邮件
		/*if(!StringUtils.isBlank(mail)){
			sendRegisterSuccessMail(mail,mailService);
		}*/
		
		//发送短信注册成功短信
		/*SmsSingleSenderResult resultMs = mobileMessageService.sendRegisterPasswordMessage(mobile);
		if(resultMs.result != 0){
			return ResultData.error(StatusCode.MESSAGE_SEND_FAIL);
		}*/
		
		//将对象转化为json字符串
		String objectJson = JSON.toJSONString(result);
		//存储进redis，命名规则openId_+对应openId字符串
		redisService.set("openId_"+openId, objectJson,60*5,TimeUnit.SECONDS);
		
		return ResultData.one(result);
	}
	
	/**
	 * 扫描获取数据自动注册
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "扫描获取数据自动注册")
    @RequestMapping(value = "/registerScanCustomer", method = RequestMethod.POST)
	public ResultData registerScanCustomer(RegisterScanDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String mobile = form.getMobile();  //手机号码
		String mail = form.getMail();  //常用邮箱
		String cardUrl = form.getCardUrl();  //名片地址
		String remark = form.getRemark();  //备注字段
		String entryKey = form.getEntryKey();  //微信加密字符串
		String nickName = form.getNickName();  //昵称
		String headerimg = form.getHeaderimg();  //用户头像
		
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(mail) || StringUtils.isBlank(cardUrl) || StringUtils.isBlank(entryKey)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证用户
		//String openId = entryKey;
		String openId = validLoginStatus(entryKey,redisService);
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.MANAGER_CODE_NOLOGIN);
		}
		
		//验证手机号码格式
		if(!PhoneFormatCheckUtils.isPhoneLegal(mobile)){
			return ResultData.error(StatusCode.MOBILE_FORMAT_ERROR);
		}
		
		//验证邮箱格式
		if(!EmailFormatCheckUtils.checkEmail(mail)){
			return ResultData.error(StatusCode.MAIL_FORMAT_ERROR);
		}
		
		//根据手机号码查询有效用户
		Customer validmobile = customerService.selectCustomerByMobile(mobile);
		if(validmobile != null){
			//return ResultData.error(StatusCode.MOBILE_IS_REGISTER);
			
			//更新openId
			if(StringUtils.isBlank(validmobile.getOpenid())){
				validmobile.setOpenid(openId);
				int count = customerService.updateCustomerOpenIdById(openId, validmobile.getId());
				if(count == 0){
					return ResultData.error(StatusCode.SYS_ERROR);
				}
			}
			
			//将对象转化为json字符串
			String mobileJson = JSON.toJSONString(validmobile);
			//存储进redis，命名规则openId_+对应openId字符串
			redisService.set("openId_"+openId, mobileJson,60*5,TimeUnit.SECONDS);
			
			return ResultData.one(validmobile);
		}
		
		//判断邮箱是否被使用
		Customer validmail = customerService.selectCustomerByMail(mail);
		if(validmail != null){
			//return ResultData.error(StatusCode.MAIL_IS_REGISTER);
			
			//更新openId
			if(StringUtils.isBlank(validmail.getOpenid())){
				validmail.setOpenid(openId);
				int count = customerService.updateCustomerOpenIdById(openId, validmail.getId());
				if(count == 0){
					return ResultData.error(StatusCode.SYS_ERROR);
				}
			}
			
			//将对象转化为json字符串
			String mailJson = JSON.toJSONString(validmail);
			//存储进redis，命名规则openId_+对应openId字符串
			redisService.set("openId_"+openId, mailJson,60*5,TimeUnit.SECONDS);
			
			return ResultData.one(validmail);
		}
		
		Customer temp = new Customer();
		temp.setRole(0);
		if(StringUtils.isNotBlank(nickName)){
			temp.setNickname(nickName);
		}else{
			
			//拆取邮箱前半段
			String[] array = mail.split("@");
			String mailName = array[0];
			
			if(StringUtils.isNotBlank(mailName)){
				temp.setNickname(mailName);
			}else{
				temp.setNickname(mobile);
			}
			
		}
		temp.setEmail(mail);
		temp.setMobile(mobile);
		temp.setPassword(MD5Utils.encode("666666"));
		temp.setCreateTime(new Date());
		temp.setMobileValidate(1);
		temp.setEmailValidate(1);
		temp.setOpenid(openId);
		temp.setName(null);
		temp.setCompany(null);
		temp.setOccupation(null);
		temp.setBcard(cardUrl);
		if(StringUtils.isBlank(headerimg)){
			temp.setHeaderimg(null);
		}else{
			temp.setHeaderimg(headerimg);
		}
		temp.setIntegration(0);
		temp.setGold(0);
		temp.setIntro(null);
		temp.setMyinfo(null);
		temp.setLabel(null);
		temp.setApply(0);
		temp.setApplyTime(null);
		temp.setDel(1);
		temp.setRemark(remark);
		
		//新增用户
		Customer result = customerService.insertCustomer(temp);
		
		//发送注册成功邮件
		//sendRegisterSuccessMail(mail,mailService);
		
		//发送短信注册成功短信
		/*SmsSingleSenderResult resultMs = mobileMessageService.sendRegisterPasswordMessage(mobile);
		if(resultMs.result != 0){
			return ResultData.error(StatusCode.MESSAGE_SEND_FAIL);
		}*/
		
		//将对象转化为json字符串
		String objectJson = JSON.toJSONString(result);
		//存储进redis，命名规则openId_+对应openId字符串
		redisService.set("openId_"+openId, objectJson,60*5,TimeUnit.SECONDS);
		
		return ResultData.one(result);
	}
	
	/**
	 * 测试邮件发送接口
	 * @param testId
	 * @return
	 */
	@ApiOperation(value = "测试邮件发送接口")
    @RequestMapping(value = "/testMail/{testId}", method = RequestMethod.GET)
    public ResultData testMail(@PathVariable Integer testId){
		
		//验证参数
		if(testId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		sendRegisterSuccessMail("871271816@qq.com",mailService);
		System.out.println(testId);
		
		return ResultData.ok();
	}
	
	/**
	 * 测试手机发送接口
	 * @param testId
	 * @return
	 */
	@ApiOperation(value = "测试手机发送接口")
    @RequestMapping(value = "/testPhone/{testId}", method = RequestMethod.GET)
    public ResultData testPhone(@PathVariable Integer testId){
		
		//验证参数
		if(testId == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		SmsSingleSenderResult resultMs = mobileMessageService.sendRegisterPasswordMessage("18896555131");
		if(resultMs.result != 0){
			return ResultData.error(StatusCode.MESSAGE_SEND_FAIL);
		}
		System.out.println(testId);
		
		return ResultData.ok();
	}
	
	/**
	 * 根据登录名判断用户使用的登录方式
	 * 返回查询到的有效用户
	 * @param loginName  登录名
	 * @param password  密码
	 * @return
	 */
	private Customer judgeLoginNameType(String loginName,String password){
		
		int judge_result = 0;  //默认为不符合任何登录方式
		
		if(EmailFormatCheckUtils.checkEmail(loginName)){
			//符合邮件格式登录方式
			judge_result = 1;
		}else if(PhoneFormatCheckUtils.isPhoneLegal(loginName)){
			//符合手机登录方式
			judge_result = 2;
		}else{
			//不符合所有允许登录方式
			judge_result = 0;
		}
		
		Customer user_result = null;
		
		//查询有效用户
		switch (judge_result){
		case 1:
			//根据邮箱和密码查询有效用户
			user_result = customerService.selectCustomerByNameAndPwd(loginName, MD5Utils.encode(password));
			break;
		case 2:
			//根据手机号和密码查询有效用户
			user_result = customerService.selectCustomerByMobileAndPwd(loginName, MD5Utils.encode(password));
			break;
		case 0:
			//不属于上述登录方式
			user_result = null;
			break;
		default:
			user_result = null;
			break;
		}
		
		return user_result;
	}
}

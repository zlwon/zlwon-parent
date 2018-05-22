package com.zlwon.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.zlwon.constant.StatusCode;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.SystemService;
import com.zlwon.utils.PhoneFormatCheckUtils;

/**
 * 系统api-用户登录，注册之类的
 * @author yuand
 *
 */
@RestController
@RequestMapping("/api/system")
public class SystemApi {

	@Autowired
	private   SystemService  systemService;
	
	/**
	 * 根据用户手机号和验证码执行登录
	 * 	1 openid和手机号不存在
	 *		添加用户，保存redis
	 *
	 * 	2 手机号存在
	 * 		数据库openid未绑定:需要把用户绑定openid，保存redis
	 * 		数据库openid和得到openid不一样，抛出异常
	 * 		数据库openid和得到openid一样，直接保存到redis
	 * 
	 * redis:openid作为key，用户信息作为value，过期时间100天
	 * @param mobile 手机号
	 * @param code 验证码
	 * @param nickName 昵称
	 * @param headerimg 头像
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public  ResultData   login(String   mobile,String  code,HttpServletRequest  request,String nickName,String headerimg){
		systemService.wxLogin(request,mobile,code,nickName,headerimg);
		return   ResultData.ok();
	}
	
	
	/**
	 * 查看用户是否登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "isLogin", method = RequestMethod.GET)
	public ResultData isLogin(HttpServletRequest  request){
		systemService.wxIsLogin(request);
		return   ResultData.ok();
	}
	
	/**
	 * 发送手机验证码
	 * @param mobile 手机号
	 * @return
	 */
    @RequestMapping(value = "sendPhoneCode", method = RequestMethod.GET)
	public ResultData sendPhoneCode(String mobile){
		
		//验证参数
		if(StringUtils.isBlank(mobile)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证手机格式-正则验证
		if(!PhoneFormatCheckUtils.isPhoneLegal(mobile)){
			return ResultData.error(StatusCode.MOBILE_FORMAT_ERROR);
		}
		
		//发送短信验证码
		SmsSingleSenderResult result = systemService.sendCodeMessage(mobile);
		
		if(result.result != 0){
			return ResultData.error(StatusCode.MESSAGE_SEND_FAIL);
		}
		return ResultData.ok();
	}
	
}

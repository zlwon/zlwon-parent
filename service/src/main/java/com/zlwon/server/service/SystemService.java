package com.zlwon.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.zlwon.dto.pc.user.UserLoginDto;

/**
 * 用于pc用户登录，注册，找回密码等操作
 * @author yuand
 *
 */
public interface SystemService {

	/**
	 * 用户登录
	 * @param request
	 * @param loginDto
	 * @return 返回token
	 */
	String userLogin(HttpServletRequest request,HttpServletResponse  response, UserLoginDto loginDto);

	/**
	 * 用户修改密码
	 * @param userLoginDto
	 * @return
	 */
	int alterPassword(UserLoginDto userLoginDto,HttpServletRequest  request);

	/**
	 * 通过短信验证码修改登录密码
	 * @param mobile
	 * @param code
	 * @param password
	 * @return
	 */
	int alterPassword(String mobile,String code, String password);

	/**
	 * 重置密码-发送验证码，,判断手机号是否存在数据库
	 * @param mobile
	 * @return
	 */
	SmsSingleSenderResult sendCodeMessage(String mobile);

}

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
	 * 注销
	 * @param request
	 */
	void userLogout(HttpServletRequest request,HttpServletResponse  response);

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
	 * 发送手机验证码,根据类型判断手机号是否存在数据库
	 * @param mobile
	 * @param type 0重置密码1注册
	 * @return
	 */
	SmsSingleSenderResult sendCodeMessage(String mobile,Integer  type);

	/**
	 * 发送手机验证码，不做任何判断
	 * @param mobile
	 * @return
	 */
	SmsSingleSenderResult sendCodeMessage(String mobile);

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
	void wxLogin(HttpServletRequest request, String mobile, String code,String nickName,String headerimg);

	void wxIsLogin(HttpServletRequest request);

	
}

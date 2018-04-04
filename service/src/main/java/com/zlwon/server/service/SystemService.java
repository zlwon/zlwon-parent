package com.zlwon.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}

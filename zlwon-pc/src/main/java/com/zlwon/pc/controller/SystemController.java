package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.dto.pc.user.UserLoginDto;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.SystemService;
import com.zlwon.utils.CookieUtils;

/**
 * 用于系统登录，注册，找回密码之类的
 * @author yuand
 *
 */
@RestController
@RequestMapping("pc/system")
public class SystemController {
	
	@Autowired
	private  SystemService  systemService;
	@Autowired
	private  CustomerService  customerService;
	
	/**
	 * 用户登录
	 * @param request
	 * @param loginDto
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	public  ResultData  login(HttpServletRequest  request,HttpServletResponse  response,UserLoginDto loginDto){
		String token = systemService.userLogin(request,response,loginDto);
		return  ResultData.one(token);
	}
	
	/**
	 * 用户注册
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="register",method=RequestMethod.POST)
	public  ResultData  register(Customer  customer){
		if(customer.getRole() == 1){//如果是知料师，申请状态为申请中
			customer.setApply(1);
		}
		customerService.saveCustomerSelective(customer);
		return  ResultData.ok();
	}
	
	/**
	 * 用户修改密码
	 * @return
	 */
	@AuthLogin
	@RequestMapping(value="editPassword",method=RequestMethod.POST)
	public  ResultData  editPassword(UserLoginDto  userLoginDto,HttpServletRequest  request){
		systemService.alterPassword(userLoginDto,request);
		return  ResultData.ok();
	}
	
	
}

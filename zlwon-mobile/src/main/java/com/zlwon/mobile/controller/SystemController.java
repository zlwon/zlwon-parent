package com.zlwon.mobile.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.user.UserLoginDto;
import com.zlwon.mobile.annotations.AuthLogin;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.SysHeaderService;
import com.zlwon.server.service.SystemService;
import com.zlwon.utils.PhoneFormatCheckUtils;

import io.swagger.annotations.Api;

/**
 * 移动端官网物性接口
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/mobile/specification")
public class SystemController extends BaseController {

	@Autowired
	private  SystemService  systemService;
	
	@Autowired
	private  CustomerService  customerService;
	
	@Autowired
	private SysHeaderService sysHeaderService;
	
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
	 * 注销
	 * @param request
	 * @return
	 */
	@RequestMapping(value="logout",method=RequestMethod.GET)
	public  ResultData  logout(HttpServletRequest  request,HttpServletResponse  response){
		systemService.userLogout(request,response);
		return  ResultData.ok();
	}
	
	/**
	 * 用户注册,肯定是普通用户
	 * @param customer 只有手机号码和密码
	 * @param code  注册验证码
	 * @return
	 */
	@RequestMapping(value="register",method=RequestMethod.POST)
	public  ResultData  register(Customer  customer,String  code){
		customerService.saveCustomerSelective(customer,code);
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
	
	
	/**
	 * 发送手机验证码,根据类型判断手机号是否存在数据库
	 * @param mobile
	 * @param type 0重置密码1注册
	 * @return
	 */
    @RequestMapping(value = "sendPhoneCode", method = RequestMethod.GET)
	public ResultData sendPhoneCode(String mobile,Integer  type){
		
		//验证参数
		if(StringUtils.isBlank(mobile)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//验证手机格式-正则验证
		if(!PhoneFormatCheckUtils.isPhoneLegal(mobile)){
			return ResultData.error(StatusCode.MOBILE_FORMAT_ERROR);
		}
		
		//发送短信验证码
		SmsSingleSenderResult result = systemService.sendCodeMessage(mobile,type);
		
		if(result.result != 0){
			return ResultData.error(StatusCode.MESSAGE_SEND_FAIL);
		}
		return ResultData.ok();
	}
    
    
    /**
     * 通过短信验证码修改登录密码
     * @param mobile 手机号
     * @param code 短信验证码
     * @param password 新密码
     * @return
     */
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public  ResultData  resetPassword(String  mobile,String  code,String  password){
    	systemService.alterPassword(mobile,code,password);
    	return ResultData.ok();
    }
}

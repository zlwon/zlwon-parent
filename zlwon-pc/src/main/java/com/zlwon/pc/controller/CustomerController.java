package com.zlwon.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.dto.pc.user.CustomerInfoDto;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.utils.CustomerUtil;

import io.swagger.annotations.Api;

/**
 * 用户api
 * @author yuand
 *
 */
@Api
@RestController
@RequestMapping("/pc/customer")
public class CustomerController {

	@Autowired
	private  CustomerService   customerService;
	
	/**
	 * 根据用户id，得到用户信息，关注前查询用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryCustomer",method=RequestMethod.GET)
	public  ResultData  queryCustomer(HttpServletRequest  request,Integer  id){
		CustomerInfoDto  customerInfoDto = customerService.findCustomerInfoByIdMake(request,id);
		return  ResultData.one(customerInfoDto);
	}
	
	
	
	
	
	
	
	
	
}

package com.zlwon.pc.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zlwon.constant.StatusCode;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.VoteActivity;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.RedisService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 共用基类
 * @author Segoul
 *
 */

@Api
@RestController
@RequestMapping("/pc/base")
public class BaseController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 根据token从redis获取用户信息
	 * @param token
	 * @return
	 */
	protected Customer accessCustomerByToken(String token){
		
		//如果token不存在则返回空
		if(StringUtils.isBlank(token)){
			return null;
		}
		
		//从redis中取出存储的用户信息
		String tokenStr = "token_"+token;
		String customerInfo = redisService.get(tokenStr);
		
		//如果从redis中取出的字段为空
		if(StringUtils.isBlank(customerInfo)){
			return null;
		}
		
		Customer user = JSON.parseObject(customerInfo,Customer.class); 
		
		return user;
	}
}

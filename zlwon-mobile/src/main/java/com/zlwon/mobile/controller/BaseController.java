package com.zlwon.mobile.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.RedisService;

import io.swagger.annotations.Api;

/**
 * 共用基类
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/mobile/base")
public class BaseController {

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CustomerService customerService;
	
	@Value("${pc.redis.user.token.prefix}")
	private String tokenPrefix;
	
	@Value("${pc.redis.user.token.field}")
	private String tokenField;
	
	/**
	 * 根据token获取用户信息
	 * @param openId
	 * @return
	 */
	protected Customer getRedisLoginCustomer(String token){
		
		Customer user = null;
		
		//验证token是否存在
		if(StringUtils.isBlank(token)){
			return null;
		}
		
		//从redis中取出存储的用户信息
		String tokenStr = tokenPrefix+token;
		String customerInfo = (String) redisService.hGet(tokenStr, tokenField);
		
		if(StringUtils.isBlank(customerInfo)){
			return null;
		}else{
			user = JSON.parseObject(customerInfo,Customer.class);
			
			//更新用户信息
			user = customerService.findCustomerById(user.getId());
		}
		
		return user;
	}
}

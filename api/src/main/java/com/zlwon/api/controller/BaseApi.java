package com.zlwon.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/base")
public class BaseApi {

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 根据openId获取用户信息
	 * @param openId
	 * @return
	 */
	protected Customer getRedisLoginCustomer(String openId){
		
		Customer user = null;
		
		//验证加密字符串是否存在
		if(StringUtils.isBlank(openId)){
			return null;
		}
		
		//根据openId获取redis中存储的用户信息值
		String redisValue = redisService.get(openId);
		if(StringUtils.isBlank(redisValue)){
			return null;
		}else{
			user = JSON.parseObject(redisValue,Customer.class);
		}
		
		return user;
	}
}

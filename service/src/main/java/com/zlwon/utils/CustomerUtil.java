package com.zlwon.utils;

import org.apache.commons.lang3.StringUtils;

import com.zlwon.rdb.entity.Customer;
import com.zlwon.server.service.RedisService;

/**
 * 从redis中得到用户信息
 * @author yuand
 *
 */
public class CustomerUtil {

	
	/**
	 * 去redis中得到用户信息,如果没有用户返回null
	 * @param token redis的key
	 * @param tokenField 用户信息字段
	 * @param redisService 
	 * @return
	 */
	public  static   Customer   getCustomer2Redis(String   token,String  tokenField,RedisService  redisService){
		String json = (String) redisService.hGet(token, tokenField);
		if(StringUtils.isBlank(json)){
			return   null;
		}
		return  JsonUtils.jsonToPojo(json, Customer.class);
	}
}

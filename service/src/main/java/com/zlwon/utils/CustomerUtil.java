package com.zlwon.utils;

import javax.servlet.http.HttpServletRequest;

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
	 * 去redis中得到用户信息,如果没有用户返回null-pc端
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
	
	/**
	 * 修改redis中用户的信息(用户提交申请状态)
	 * @param token redis的key
	 * @param tokenField 用户信息字段
	 * @param customerJson 修改后的用户信息json
	 * @param redisService
	 */
	public  static   void   resetCustomer2Redis(String   token,String  tokenField,Object  customerJson,RedisService  redisService){
		redisService.hSet(token, tokenField, customerJson);
	}
	
	/**
	 * wx接口从请求头中获取openid
	 * @param request
	 * @param key 请求头中保存openid的key
	 * @return
	 */
	public static String   getOpenid(HttpServletRequest request,String  key){
		return    request.getHeader(key);
	}
	
	/**
	 * 去redis中得到用户信息,如果没有用户返回null-api端
	 * @param key 请求头中保存openid的key
	 * @param redisService 
	 * @return
	 */
	public  static   Customer   getCustomer2Redis(HttpServletRequest request,String  key,RedisService  redisService){
		//得到openid值
		String openid = getOpenid(request, key);
		if(StringUtils.isBlank(openid)){
			return  null;
		}
		//根据openid值得到用户信息
		String json = redisService.get(openid);
		if(StringUtils.isBlank(json)){
			return   null;
		}
		return  JsonUtils.jsonToPojo(json, Customer.class);
	}
}

package com.zlwon.api.controller;

import com.alibaba.fastjson.JSON;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.MailService;
import com.zlwon.server.service.RedisService;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

public class BaseApi {

	/**
	 * 验证用户是否处于登录状态
	 * @param entryKey
	 * @return
	 */
	protected String validLoginStatus(String entryKey, RedisService redisService){
		
		String openId = "";
		
		//验证加密字符串是否存在
		if(StringUtils.isBlank(entryKey)){
			return "";
		}
		
		//从redis中获取加密字符串的内容
		String mdStr = redisService.get("encryKey_"+entryKey);
		if(StringUtils.isBlank(mdStr)){
			return "";
		}else{
			if(!entryKey.equals(mdStr)){
				return "";
			}
		}
		
		String[] arr = mdStr.split("_");
		openId = arr[0];
		
		return openId;
	}
	
	/**
	 * 根据openId获取redis中的用户信息
	 * @param openId
	 * @return
	 */
	protected Customer getRedisCustomer(String openId,RedisService redisService,CustomerService customerService){
		
		Customer user = null;
		String jsonStr = "";
		
		//验证openId是否存在于redis中,命名规则openId_+对应openId字符串
		String redisValue = redisService.get("openId_"+openId);
		if(StringUtils.isBlank(redisValue)){  //如果缓存不存在
			//根据openId查询数据库
			user = customerService.selectCustomerByOpenId(openId);
			if(user != null){
				//将对象转化为json字符串
				String objectJson = JSON.toJSONString(user);
				jsonStr = objectJson;
				//存储进redis，命名规则openId_+对应openId字符串
				redisService.set("openId_"+openId, jsonStr,60*5,TimeUnit.SECONDS);
			}
		}else{
			jsonStr = redisValue;
			//将json字符串转化为对象
			user = JSON.parseObject(jsonStr,Customer.class); 
		}
		
		return user;
	}
	
	/**
	 * 采用创建线程方式发送注册成功邮件
	 * @param mailTo
	 */
	protected void sendRegisterSuccessMail(String mailTo,MailService mailService){
		new Thread(new Runnable() {
            @Override
            public void run() {
            	//拼接发送内容
    	    	StringBuffer text = new StringBuffer();
    	    	text.append("<div style='text-align:left'>");
    	    	text.append("<div>欢迎使用知料网</div>");
    	    	text.append("<br />");
    	    	text.append("<div>恭喜您注册知料网用户成功，您的初始密码为666666，可登陆官网后进行修改，谢谢您的使用！</div>");
    	    	text.append("<br />");
    	    	text.append("<div>知料网团队</div>");
    	    	text.append("</div>");
    			
    			mailService.sendHtmlMail(mailTo, "知料网注册成功通知邮件", text.toString());
            }
        }).start();
	}
}

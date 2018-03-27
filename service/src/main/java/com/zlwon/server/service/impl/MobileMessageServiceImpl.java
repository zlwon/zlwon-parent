package com.zlwon.server.service.impl;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.zlwon.server.service.MobileMessageService;
import com.zlwon.sms.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 短信Service
 * 腾讯云接口
 * @author yangy
 *
 */

@Service
public class MobileMessageServiceImpl implements MobileMessageService {

	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 发送短信验证码
	 * @param mobile  手机号（不带国家码）
	 * @return
	 */
	public SmsSingleSenderResult sendCodeMessage(String mobile){
		
		SmsSingleSenderResult result = null;
		
		try {
			//生成随机短信验证码
			String mobile_code = String.valueOf((int)((Math.random()*9+1)*100000));
			
			//发送短信验证码
			SmsSender sender = new SmsSender();
			ArrayList<String> params =new ArrayList<String>();
			params.add(mobile_code);
			params.add("10");
			result = sender.sendSmsWithTemplate("86", mobile, 93306, params, "", "", "");
			
			//将短信验证码存储入redis，命名规则mobilecode_+对应mobile符串
			//短信验证码10分钟内有效
			stringRedisTemplate.opsForValue().set("mobilecode_"+mobile, mobile_code,60*10,TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 注册成功发送用户默认密码到用户邮箱
	 * @param mobile  手机号（不带国家码）
	 * @return
	 */
	public SmsSingleSenderResult sendRegisterPasswordMessage(String mobile){
		
		SmsSingleSenderResult result = null;
		
		try {
			//发送短信
			SmsSender sender = new SmsSender();
			ArrayList<String> params =new ArrayList<String>();
			params.add(mobile);
			params.add("666666");
			result = sender.sendSmsWithTemplate("86", mobile, 93312, params, "", "", "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}

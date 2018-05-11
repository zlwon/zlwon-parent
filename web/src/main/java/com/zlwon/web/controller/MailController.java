package com.zlwon.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.constant.StatusCode;
import com.zlwon.dto.web.mailsend.SystemExtensionUserDto;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.MailService;
import com.zlwon.web.annotations.AuthLogin;

/**
 * 邮件相关接口
 * @author yangy
 *
 */

@AuthLogin
@RestController
@RequestMapping("mail")
public class MailController {

	@Autowired
	private MailService mailService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 发送系统推广邮件
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "sendSystemExtensionMail", method = RequestMethod.POST)
	public ResultData sendSystemExtensionMail(SystemExtensionUserDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String mailStr = form.getMailStr();  //邮箱地址拼接字符串
		String mailTheme = form.getMailTheme();  //邮件主题
		
		if(StringUtils.isBlank(mailStr) || StringUtils.isBlank(mailTheme)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//将邮箱地址拆分
		String mailArr[] = mailStr.split(",");
		
		//循环发送邮件
		for(int i=0;i<mailArr.length;i++){
			Map<String, Object> model = new HashMap<String, Object>();
			
			mailService.sendVelocityTemplateMail(mailArr[i], mailTheme, "extensionToUser.vm",model);
		}
		
		return ResultData.ok();
	}
}

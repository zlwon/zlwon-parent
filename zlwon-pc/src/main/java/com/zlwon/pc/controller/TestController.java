package com.zlwon.pc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.rest.ResultData;
import com.zlwon.server.service.MailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/pc/test")
public class TestController extends BaseController {
	
	@Autowired
	private MailService mailService;

	@ApiOperation(value = "测试环境")
    @RequestMapping(value = "/testHello", method = RequestMethod.GET)
    public ResultData testHello(@RequestParam String text){
	
		String result = "";
		
		return ResultData.one(result);
	}
	
	@ApiOperation(value = "测试环境-邮件")
    @RequestMapping(value = "/testMail", method = RequestMethod.GET)
    public ResultData testMail(){
	
		String sendMail = "cy871271816dmr@163.com";
		
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("url", "https://api.zlwon.com/upload/15231/013a39f4236748ca83877dbdb06f69a2.jpg");
        model.put("currentDate", "2016-03-31");
		
		mailService.sendVelocityTemplateMail(sendMail, "你好雨哥哥111", "testvm.vm",model);
		
		return ResultData.ok();
	}
}

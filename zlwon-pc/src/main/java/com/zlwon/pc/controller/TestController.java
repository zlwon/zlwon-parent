package com.zlwon.pc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zlwon.dto.mail.MailParamForm;
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
    @RequestMapping(value = "/testSingleMail", method = RequestMethod.GET)
    public ResultData testSingleMail(){
	
		String sendMail = "cy871271816dmr@163.com";
		
		System.out.println("start!!!!");
		
		mailService.sendHtmlMail(sendMail, "你好雨哥哥PDF","1213313");
		
		System.out.println("success!!!!");
		
		return ResultData.ok();
	}
	
	@ApiOperation(value = "测试环境-邮件")
    @RequestMapping(value = "/testMail", method = RequestMethod.GET)
    public ResultData testMail(){
	
		String sendMail = "cy871271816dmr@163.com";
		
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", "871271816@qq.com");
        model.put("specName", "VS50");
		
		mailService.sendVelocityTemplateMail(sendMail, "你好雨哥哥PDF", "specPdf.vm",model);
		
		return ResultData.ok();
	}
	
	@ApiOperation(value = "测试环境-Attach邮件")
    @RequestMapping(value = "/testAttachMail", method = RequestMethod.GET)
    public ResultData testAttachMail(){
	
		String sendMail = "cy871271816dmr@163.com";
		
        String pdfUrl = "/usr/local/www/upload/specPdf/TS_V0_E.pdf";
        //String pdfUrl = "H:/download/images/2018032110544854483986.jpg";
        
        System.out.println("start!!!!");
        
        mailService.sendAttachMail(sendMail,"呢喃细语","hahahahah",pdfUrl.replace("\\", "//"),"TS_V0_E","pdf");
        
        System.out.println("success!!!!");
		
		return ResultData.ok();
	}
	
	@ApiOperation(value = "测试环境-VelocityAttach邮件")
    @RequestMapping(value = "/testVelocityAttachMail", method = RequestMethod.GET)
    public ResultData testVelocityAttachMail(){
	
		String sendMail = "cy871271816dmr@163.com";
		
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", "871271816@qq.com");
        model.put("specName", "TS_V0_E");
		
        String pdfUrl = "upload/specPdf/TS_V0_E.pdf";
        
        MailParamForm form = new MailParamForm();
        form.setMailTo(sendMail);
        form.setTitle("呢喃细语");
        form.setTemplateName("specPdf.vm");
        form.setModel(model);
        form.setFilePath(pdfUrl.replace("\\", "//"));
        form.setFileName("TS_V0_E");
        form.setFileType("pdf");
        
        System.out.println("start!!!!");
        
        mailService.sendVelocityTemplateAttachMail(form);
        
        System.out.println("success!!!!");
		
		return ResultData.ok();
	}
}

package com.zlwon.server.service.impl;

import com.zlwon.dto.mail.MailParamForm;
import com.zlwon.server.service.MailService;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.ResourceUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件ServiceImpl
 * @author yangy
 *
 */

@Service
public class MailServiceImpl implements MailService {

	@Autowired
    private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
    private String sender;  //发送者
	
	@Value("${vm.store.address}")
    private String vmStore;  //vm模板存放地址
	
	/*@Autowired
    private VelocityEngine velocityEngine;*/
	
	/**
	 * 发送HTML页面的邮件（包括普通邮件）
	 * @param mailTo  邮件接收地址
	 * @param title  邮件标题
	 * @param content  邮件内容
	 */
	public void sendHtmlMail(String mailTo, String title, String content){
		//配置邮件基本信息
		MimeMessage message = null;
		try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(sender);
            helper.setTo(mailTo);
            helper.setSubject(title);
            helper.setText(content, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		//发送邮件
        mailSender.send(message);
	}
	
	/**
	 * 发送带有附件的邮件
	 * @param mailTo  邮件接收地址
	 * @param title  邮件标题
	 * @param content  邮件内容
	 * @param filePath  文件地址
	 * @param fileName  文件名称
	 * @param fileType  文件类型后缀
	 */
	public void sendAttachMail(String mailTo,String title,String content,String filePath,String fileName,String fileType){
		//配置邮件基本信息
    	MimeMessage message = null;
    	try{
    		message = mailSender.createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(message, true);
    		helper.setFrom(sender);
    		helper.setTo(mailTo);
    		helper.setSubject(title);
    		helper.setText(content,true);
    		FileSystemResource file = new FileSystemResource(new File(filePath));
    		helper.addAttachment(fileName+"."+fileType, file);
    	} catch (Exception e) {
            e.printStackTrace();
        }
    	
    	//发送邮件
    	mailSender.send(message);
	}
	
	/**
	 * 发送velocity模板邮件
	 * @param mailTo  邮件接收地址
	 * @param title  邮件标题
	 * @param templateName  模板名称
	 * @param model  参数
	 */
	public void sendVelocityTemplateMail(String mailTo,String title,String templateName,Map model){
		//配置邮件基本信息
    	MimeMessage message = null;
    	try{
    		message = mailSender.createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(message, true);
    		helper.setFrom(sender);
    		helper.setTo(mailTo);
    		helper.setSubject(title);
    		
    		//初始化velocity引擎
    		//String fileDir = MailServiceImpl.class.getResource("/templates").getPath();
    		VelocityEngine velocityEngine = new VelocityEngine();
    		Properties properties = new Properties();
    		properties.setProperty(velocityEngine.FILE_RESOURCE_LOADER_PATH, vmStore);
    		velocityEngine.init(properties);
    		
    		helper.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "utf-8", model),true);
    	} catch (Exception e) {
            e.printStackTrace();
        }
    	
    	//发送邮件
    	mailSender.send(message);
	}
	
	/**
	 * 发送带有附件的velocity模板邮件
	 * @param form
	 */
	public void sendVelocityTemplateAttachMail(MailParamForm form){
		//配置邮件基本信息
    	MimeMessage message = null;
    	try{
    		message = mailSender.createMimeMessage();
    		MimeMessageHelper helper = new MimeMessageHelper(message, true);
    		helper.setFrom(sender);
    		helper.setTo(form.getMailTo());
    		helper.setSubject(form.getTitle());
    		
    		//初始化velocity引擎
    		//String fileDir = MailServiceImpl.class.getResource("/templates").getPath();
    		VelocityEngine velocityEngine = new VelocityEngine();
    		Properties properties = new Properties();
    		properties.setProperty(velocityEngine.FILE_RESOURCE_LOADER_PATH, vmStore);
    		velocityEngine.init(properties);
    		
    		helper.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, form.getTemplateName(), "utf-8", form.getModel()),true);
    		
    		//添加附件
    		FileSystemResource file = new FileSystemResource(new File(form.getFilePath()));
    		helper.addAttachment(form.getFileName()+"."+form.getFileType(), file);
    		
    	} catch (Exception e) {
            e.printStackTrace();
        }
    	
    	//发送邮件
    	mailSender.send(message);
	}
}

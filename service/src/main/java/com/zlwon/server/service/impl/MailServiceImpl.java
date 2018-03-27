package com.zlwon.server.service.impl;

import com.zlwon.server.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

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
}

package com.zlwon.server.service;

import java.util.Map;

import com.zlwon.dto.mail.MailParamForm;

/**
 * 邮件Service
 * @author yangy
 *
 */

public interface MailService {

	/**
	 * 发送HTML页面的邮件（包括普通邮件）
	 * @param mailTo  邮件接收地址
	 * @param title  邮件标题
	 * @param content  邮件内容
	 */
	void sendHtmlMail(String mailTo, String title, String content);
	
	/**
	 * 发送带有附件的邮件
	 * @param mailTo  邮件接收地址
	 * @param title  邮件标题
	 * @param content  邮件内容
	 * @param filePath  文件地址
	 * @param fileName  文件名称
	 * @param fileType  文件类型后缀
	 */
	void sendAttachMail(String mailTo,String title,String content,String filePath,String fileName,String fileType);
	
	/**
	 * 发送velocity模板邮件
	 * @param mailTo  邮件接收地址
	 * @param title  邮件标题
	 * @param templateName  模板名称
	 * @param model  参数
	 */
	void sendVelocityTemplateMail(String mailTo,String title,String templateName,Map model);
	
	/**
	 * 发送带有附件的velocity模板邮件
	 * @param form
	 */
	void sendVelocityTemplateAttachMail(MailParamForm form);
}

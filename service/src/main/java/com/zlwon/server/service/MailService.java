package com.zlwon.server.service;

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
}

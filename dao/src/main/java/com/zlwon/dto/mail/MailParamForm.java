package com.zlwon.dto.mail;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 邮件参数对象
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class MailParamForm {

	//基本
	private String mailFrom;  //邮件发送地址
	
	private String mailTo;  //邮件接收地址
	
	private String title;  //邮件标题
	
	private String content;  //邮件内容
	
	//主要用于附件
	private String filePath;  //文件地址
	
	private String fileName;  //文件名称
	
	private String fileType;  //文件类型后缀
	
	//主要用于velocity模板邮件
	private String templateName;  //模板名称
	
	private Map model;  //模板参数
}

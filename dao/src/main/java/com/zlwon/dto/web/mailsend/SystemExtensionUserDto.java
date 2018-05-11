package com.zlwon.dto.web.mailsend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * web端系统推广邮件参数入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class SystemExtensionUserDto {

	private String mailStr;  //邮箱地址拼接字符串
	
	private String mailTheme;  //邮件主题
}

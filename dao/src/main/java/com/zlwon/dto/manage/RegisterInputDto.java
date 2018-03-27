package com.zlwon.dto.manage;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 手动录入注册参数入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class RegisterInputDto implements Serializable {

	private String mobile;  //手机号码
	
	private String mobileCode;  //短信验证码
	
	private String mail;  //常用邮箱
	
	private String entryKey;  //微信加密字符串
}

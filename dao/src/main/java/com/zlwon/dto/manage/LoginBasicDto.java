package com.zlwon.dto.manage;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 登录基础参数入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class LoginBasicDto implements Serializable {

	private String loginName;  //登录账户
	
	private String password;  //登录密码，MD5加密模式
	
	private String entryKey;  //微信加密字符串
}

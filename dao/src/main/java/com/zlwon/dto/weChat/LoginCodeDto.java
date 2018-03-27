package com.zlwon.dto.weChat;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 微信登录凭证
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class LoginCodeDto implements Serializable {

	private String appid;  //小程序唯一标识
	
	private String secret;  //小程序的 app secret
	
	private String js_code;  //登录时获取的 code
	
	private String grant_type;  //填写为 authorization_code
}

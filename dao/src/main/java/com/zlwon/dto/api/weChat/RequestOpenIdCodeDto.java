package com.zlwon.dto.api.weChat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 申请openId相关参数接口入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class RequestOpenIdCodeDto {

	private String appid;  //小程序唯一标识
	
	private String secret;  //小程序的 app secret
	
	private String js_code;  //登录时获取的 code
	
	private String grant_type;  //填写为 authorization_code
}

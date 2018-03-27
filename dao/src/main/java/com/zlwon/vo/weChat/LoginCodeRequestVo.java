package com.zlwon.vo.weChat;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 微信登录凭证请求返回参数
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class LoginCodeRequestVo implements Serializable {

	private String openid;  //用户唯一标识
	
	private String session_key;  //会话密钥
	
	private String unionid;  //用户在开放平台的唯一标识符。本字段在满足一定条件的情况下才返回
}

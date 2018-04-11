package com.zlwon.vo.weChat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 根据微信登录凭证获取session_key和openid返回参数
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class RequestOpenIdByLoginCodeVo {

	private String entryKey;  //加密字符串
	
	private Integer isExist;  //用户是否存在 1：存在，0：不存在
}

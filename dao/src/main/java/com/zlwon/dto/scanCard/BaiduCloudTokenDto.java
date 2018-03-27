package com.zlwon.dto.scanCard;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 获取百度云token入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class BaiduCloudTokenDto implements Serializable {

	private String clientId;  //必须参数，应用的API Key
	
	private String clientSecret;  //必须参数，应用的Secret Key
}

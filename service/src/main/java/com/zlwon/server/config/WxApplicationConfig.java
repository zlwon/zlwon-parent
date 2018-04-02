package com.zlwon.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 微信小程序appid，秘钥，生产二维码路径
 * @author FelixChen
 *
 */
@Data
@Component
@ConfigurationProperties(prefix="wx.application.qr")
public class WxApplicationConfig {

	private  String   appid;
	private  String   secret;
	private  String   path;
}

package com.zlwon.dto.scanCard;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 扫描百度云OCR注册入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ScanBaiduCloudOcrRegisterDto implements Serializable {

	private String url;  //图片网络路径
	
	private String languageType;  //识别语言类型,默认为CHN_ENG
	
	private String detectDirection;  //是否检测图像朝向，默认false
	
	private String detectLanguage;  //是否检测语言，默认false
	
	private String probability;   //是否返回识别结果中每一行的置信度,默认false
	
	private String accessToken;  //token
	
	private String nickName;  //昵称
	
	private String headerimg;  //用户头像
	
	private String entryKey;  //微信加密字符串
}

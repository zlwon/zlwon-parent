package com.zlwon.dto.consultation;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 添加用户咨询入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AddConsultationDto implements Serializable {
	
	private String entryKey;  //微信加密字符串
	
	private Integer cid;  //展会案例ID
	
	private String title;  //咨询标题
	
	private String content;  //咨询详情
	
	private String contentVoice;  //语音内容
	
}

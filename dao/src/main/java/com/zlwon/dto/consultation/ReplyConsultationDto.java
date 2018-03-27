package com.zlwon.dto.consultation;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 回复用户咨询入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ReplyConsultationDto implements Serializable {

	private Integer consultationId;  //用户咨询ID
	
	private String replyCont;  //回复内容
	
	private String replyContVoice;  //语音回复内容
	
	private String entryKey;  //微信加密字符串
}

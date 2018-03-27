package com.zlwon.vo.consultation;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户咨询详细出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ConsultationDetailVo implements Serializable {

	private Integer id;
	
	private Integer uid;  //用户ID
	
	private Integer cid;  //展会案例ID
	
	private Integer sid;  //工程师ID
	
	private String title;  //咨询标题
	
	private String content;  //咨询详情
	
	private String contentVoice;  //语音内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建时间
	
	private String replyCont;  //回复内容
	
	private String replyContVoice;  //语音回复内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date replyTime;  //回复时间
	
	private String nickName;  //用户昵称
	
	private String headerimg;  //用户头像
	
	private String engineerName;  //工程师昵称
	
	private String engineerimg;  //工程师头像
}

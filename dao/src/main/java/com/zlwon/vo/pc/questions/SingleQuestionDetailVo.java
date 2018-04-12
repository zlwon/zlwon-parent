package com.zlwon.vo.pc.questions;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端问题详情(包括该问题的回答)出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class SingleQuestionDetailVo {

	private Integer id;
	
	private Integer uid;  //用户ID
	
	private String nickname;  //昵称
	
	private String headerimg;  //用户头像
	
	private Integer iid;  //信息ID
	
	private String source;  //来源名称
	
	private Integer infoClass;  //信息类别：1:物性、2:案例
	
	private String title;  //提问标题
	
	private String content;  //问题内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private Integer answerCount;  //回答数量
	
	private Integer isCollect;  //是否收藏 1：是，0：否
	
	private Integer collectId;  //收藏ID
}

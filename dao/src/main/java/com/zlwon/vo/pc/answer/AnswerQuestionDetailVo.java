package com.zlwon.vo.pc.answer;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端回答问题详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AnswerQuestionDetailVo {

	private Integer id;
	
	private Integer uid;  //用户ID
	
	private String nickname;  //昵称
	
	private String headerimg;  //用户头像
	
	private Integer qid;  //问题ID
	
	private String content;  //回答内容
	
	private Integer supportNums;//点赞总数
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建时间
	
	private Integer quesUid;  //提问用户ID
	
	private String quesNickname;  //提问用户昵称
	
	private String quesHeaderimg;  //提问用户头像
	
	private Integer iid;  //信息ID
	
	private String source;  //来源名称
	
	private Integer infoClass;  //信息类别：1:物性、2:案例
	
	private String title;  //提问标题
	
	private String quesContent;  //问题内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date quesCreateTime;  //问题创建日期
}

package com.zlwon.vo.pc.answer;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 提问回答详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AnswerDetailVo {

	private Integer id;
	
	private Integer uid;  //用户ID
	
	private String nickname;  //昵称
	
	private String headerimg;  //用户头像
	
	private String intro;  //一句话介绍
	
	private Integer qid;  //问题ID
	
	private String content;  //回答内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建时间
	
	private Integer supportNums;//点赞总数
	
	private Integer isSupport;  //是否点赞 0：未点赞，1：已点赞
	
	private Integer isAnonymous;  //是否匿名  0：非匿名 1：匿名
}

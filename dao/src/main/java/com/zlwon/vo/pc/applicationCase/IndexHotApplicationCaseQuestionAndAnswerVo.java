package com.zlwon.vo.pc.applicationCase;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * pc首页热门案例对应的问答信息
 * @author yuand
 *
 */
@Data
public class IndexHotApplicationCaseQuestionAndAnswerVo {

	private   Integer   id;//提问id
	
	private   String   title;//提问标题
	
	private   String   questionContent;//提问对应得回答内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private   Date   createTime;//提问时间
	
	private   String   nickname;//提问用户的昵称
	
	private   String   content;//提问对应得回答内容
	
}

package com.zlwon.vo.pc.applicationCase;

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
	
	private   String   createTime;//提问时间
	
	private   String   nickname;//提问用户的昵称
	
	private   String   content;//提问对应得回答内容
	
}

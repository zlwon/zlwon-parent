package com.zlwon.vo.questions;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * web端提问列表
 * @author yuand
 *
 */
@Data
public class QuestionsListVo {

	private   Integer   id;//提问id
	
	private   String   title;//提问标题
	
	private   String   content;//提问内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private   Date   createTime;//创建时间
	
	private   Integer  examine;//用户创建数据审核结果，0未审核，1审核通过；先通过后审核机制
	
	private   String   infoTitle;//提问对象标题(案例标题或者物性规格名称)
	
	private   String   nickname;//提问用户昵称
	
	private   Integer  uid;//提问用户id
}

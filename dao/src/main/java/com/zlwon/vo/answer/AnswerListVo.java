package com.zlwon.vo.answer;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * web端回答列表
 * @author yuand
 *
 */
@Data
public class AnswerListVo {

	private  Integer  id;//回答id
	
	private  String   content;//回答内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private  Date   createTime;//回答时间
	
	private  String   infoTitle;//提问对象标题(案例标题或者物性规格名称)
	
	private  String   questionContent;//提问内容
	
	private  Integer  examine;//用户创建数据审核结果，0未审核，1审核通过；先通过后审核机制
	
	private  String   nickname;//回答用户昵称
	
	private  String   headerimg;//回答用户头像
}

package com.zlwon.vo.answerRecord;

import lombok.Data;

/**
 * web端管理员指定案例(物性)邀请回答的用户
 * @author yuand
 *
 */
@Data
public class AnswerRecordListVo {

	private  Integer  id;//邀请(zl_answer_recommend)id
	
	private  Integer  type;//类型：1物性  2案例
	
	private  String   title;//案例(物性)标题
	
	private  String  nickname;//指定邀请人昵称
	
	private  String  headerimg;//指定邀请人头像
	
	private  Integer  role;//指定邀请人类型账户类型，0普通用户，1认证用户(必须上传自己名片)，2生产商(物性生产商，公司简介)，3游客，4经销商，5终端用户，6企业用户(必须自己名片,选择企业)
	
	private  String  email;//指定邀请人邮箱
	
	private  String  company;//指定邀请人公司名称
}

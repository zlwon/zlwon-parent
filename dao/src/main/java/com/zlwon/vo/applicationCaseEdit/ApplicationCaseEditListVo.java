package com.zlwon.vo.applicationCaseEdit;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * web端用户编辑案例列表
 * @author yuand
 *
 */
@Data
public class ApplicationCaseEditListVo {
	private  Integer   id;//编辑案例id
	
	private  String   title;//案例标题
	
	private  String   selectRequirements;//编辑后的选材要求
	
	private  String   selectCause;//编辑后的选材原因
	
	private  String   setting;//编辑后的案例背景
	
	private  Integer  examine;//用户创建数据审核结果，0未审核，1审核通过，2驳回，需要把这3条数据同步到案例表中
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private  Date  auditTime;//审核通过更新日期
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private  Date  createTime;//编辑时间
	
	private String  nickname;//编辑用户昵称
	
	private String  headerimg;//编辑用户头像

	private  Integer  aid;//案例id
}

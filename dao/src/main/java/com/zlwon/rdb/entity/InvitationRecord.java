package com.zlwon.rdb.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 企业账户添加员工账户记录
 * @author yangy
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class InvitationRecord implements Serializable {

	private Integer id;  //id
	
	private Integer pid;  //企业类型账户ID
	
	private Integer uid;  //接受邀请的知料师账户类型
	
	private Integer invitation;  //邀请接受状态，0邀请确认中，1接受，-1拒绝
}

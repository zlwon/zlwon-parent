package com.zlwon.vo.invitationRecord;

import lombok.Data;

@Data
public class InvitationRecordVo {

	private  Integer  id;//邀请id
	
	private  Integer  pid;//企业用户id
	private  String   company;//企业名称
	
	private  Integer  uid;//知料师用户id
	private  String   nickname;//知料师昵称
	private  String   name;//知料师真实姓名
	
	
	private  Integer  invitation;//邀请接受状态，0邀请确认中，1接受，-1拒绝
}

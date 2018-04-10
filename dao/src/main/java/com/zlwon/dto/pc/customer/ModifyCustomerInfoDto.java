package com.zlwon.dto.pc.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端编辑个人信息入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ModifyCustomerInfoDto {

	private String realName;  //真实姓名
	
	private String headerimg;  //用户头像
	
	private String nickname;  //用户昵称
	
	private String companyName;  //公司名称
	
	private String occupation;  //职业经历
	
	private String intro;  //一句话简介
	
	private String myinfo;  //个人简介
	
	private String label;  //业务标签
}

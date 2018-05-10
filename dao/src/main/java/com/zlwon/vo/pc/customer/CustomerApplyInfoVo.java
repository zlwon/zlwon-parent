package com.zlwon.vo.pc.customer;

import java.util.List;

import com.zlwon.rdb.entity.CharacteristicBusiness;

import lombok.Data;

/**
 * 用户认证信息
 * @author yuand
 *
 */

@Data
public class CustomerApplyInfoVo {

	private String nickname;  //昵称
	
	private String email;  //注册邮箱
	
	private String occupation;   //职业经历
	
	private String myinfo;  //个人简介
	
	private String bcard;//名片路径
	
	private String label;//业务标签id，多个逗号隔开
	
	private List<CharacteristicBusiness> characterList;  //个人业务标签列表
	
	private String companyShortName;//企业简称
	
	private String companyFullName;//企业全称
	
	private String charter;//营业执照
	
	private String intro;//企业介绍
	
	private String linkPerson;//企业联系人
	
	private String linkEmail;//企业联系人邮箱

    private String linkTel;//企业联系人电话
	
}

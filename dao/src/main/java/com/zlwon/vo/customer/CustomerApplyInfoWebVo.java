package com.zlwon.vo.customer;

import java.util.List;

import com.zlwon.rdb.entity.CharacteristicBusiness;

import lombok.Data;

/**
 * web用户认证信息
 * @author yuand
 *
 */
@Data
public class CustomerApplyInfoWebVo {

	private Integer  id;//认证id（zl_customer_auth表id）
	
	private Integer  uid;//提交认证用户id
	
	private Integer  type;//用户认证类型1:个人认证6企业认证
	
	private String nickname;  //提交认证昵称
	
	private String email;  //提交认证注册邮箱
	
	private String occupation;   //提交认证职业经历
	
	private String myinfo;  //提交认证个人简介
	
	private String bcard;//提交认证名片路径
	
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

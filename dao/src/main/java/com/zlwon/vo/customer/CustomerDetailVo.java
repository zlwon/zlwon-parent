package com.zlwon.vo.customer;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户信息详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class CustomerDetailVo implements Serializable {

	private Integer id;
	
	private Integer role;  //账户类型，0普通用户，1知料师，2企业
	
	private String nickname;  //昵称
	
	private String name;  //真实姓名
	
	private String mobile;  //手机号码
	
	private String company;  //公司名称
	
	private String occupation;   //职业经历
	
	private String bcard;   //知料师名片
	
	private String headerimg;  //用户头像
	
	private Integer integration;  //积分
	
	private Integer gold;  //知料币
	
	private String email;  //注册邮箱
	
	private String intro;  //一句话介绍
	
	private String myinfo;  //个人简介
	
	private String label;  //业务标签
	
	private Integer apply;  //知料师申请状态，0正常，1申请中，2申请通过，-1申请失败
	
	private String openid;  //绑定微信账户ID
	
	private String remark;  //备注字段
	
	private Integer identity;  //身份 1：工程师，0：非工程师
}

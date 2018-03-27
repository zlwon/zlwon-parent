package com.zlwon.rdb.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户表（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class Customer  implements Serializable{

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
	
	private String password;  //登录密码，MD5加密模式
	
	private String intro;  //一句话介绍
	
	private String myinfo;  //个人简介
	
	private String label;  //业务标签
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private Integer apply;  //知料师申请状态，0正常，1申请中，2申请通过，-1申请失败
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date applyTime;  //知料师申请日期
	
	private Integer emailValidate;  //邮箱验证状态，0未验证，1已验证
	
	private Integer mobileValidate;  //手机验证状态，0未验证，1已验证
	
	private Integer del;  //标记删除状态，1正常，-1已删除
	
	private String openid;  //绑定微信账户ID
	
	private String remark;  //备注字段
}

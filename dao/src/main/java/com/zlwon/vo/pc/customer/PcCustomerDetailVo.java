package com.zlwon.vo.pc.customer;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zlwon.rdb.entity.CharacteristicBusiness;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端用户详情出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class PcCustomerDetailVo {

	private Integer id;
	
	private Integer role;  //账户类型，0普通用户，1知料师，2企业，3游客
	
	private Integer roleType;  //头衔，role=1时才有,0:无1:知料师2:高级知料师3:首席知料师
	
	private Integer roleApply;//用户申请成为的类型：-1不申请1认证用户6企业用户
	
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
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
	
	private Integer apply;  //知料师申请状态，0正常，1申请中，2申请通过，-1申请失败
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date applyTime;  //知料师申请日期
	
	private Integer emailValidate;  //邮箱验证状态，0未验证，1已验证
	
	private Integer mobileValidate;  //手机验证状态，0未验证，1已验证
	
	private List<CharacteristicBusiness> characterList;  //个人业务标签列表
}

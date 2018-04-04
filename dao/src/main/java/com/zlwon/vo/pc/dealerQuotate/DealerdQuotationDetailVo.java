package com.zlwon.vo.pc.dealerQuotate;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DealerdQuotationDetailVo {

	private Integer id;  //自增ID
	
	private Integer uid;  //企业账户类型用户ID
	
	private Integer sid;  //物性表Id
	
	private String nickname;  //昵称
	
	private String name;  //真实姓名
	
	private String company;  //公司名称
	
	private String headerimg;  //头像
	
	private Float price;  //报价
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;  //报价更新日期
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建日期
}

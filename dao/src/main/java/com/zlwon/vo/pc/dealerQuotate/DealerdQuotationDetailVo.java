package com.zlwon.vo.pc.dealerQuotate;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rdb.entity.SpecificationParameter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc物性表材料报价单出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class DealerdQuotationDetailVo  {

	private Integer id;  //自增ID
	
	private Integer uid;  //企业账户类型用户ID
	
	private Integer sid;  //物性表Id
	
	private String specName;  //物性规格名称
	
	private String nickname;  //昵称
	
	private String company;  //公司名称
	
	private String headerimg;  //头像
	
	private String intro;  //一句话简介
	
	private String color;  //颜色/色号
	
	private Float price;  //报价
	
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date validityDate;  //有效期（截止日期，3个月内）

    private Integer orderQuantity;  //起订量

    private String deliveryDate;  //交货期

    private String deliveryPlace;  //交货地点

    private String payMethod;  //支付方式

    private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过，2驳回

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;  //创建日期
}

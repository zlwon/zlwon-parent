package com.zlwon.rdb.entity;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 物性表材料报价记录（mysql）实体
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class DealerdQuotation {
	
    private Integer id;  //自增ID

    private Integer uid;  //企业账户类型用户ID

    private Integer sid;  //物性表Id

    private String color;  //颜色/色号

    private Float price;  //报价

    private Date validityDate;  //有效期（截止日期，3个月内）

    private Integer orderQuantity;  //起订量

    private String deliveryDate;  //交货期

    private String deliveryPlace;  //交货地点

    private String payMethod;  //支付方式

    private Integer examine;  //用户创建数据审核结果，0未审核，1审核通过，2驳回

    private Date createTime;  //创建日期
}
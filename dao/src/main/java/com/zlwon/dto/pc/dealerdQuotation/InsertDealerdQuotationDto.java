package com.zlwon.dto.pc.dealerdQuotation;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端新增材料报价单入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class InsertDealerdQuotationDto {

	private Integer sid;  //物性表Id

    private String color;  //颜色/色号

    private Float price;  //报价

    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date validityDate;  //有效期（截止日期，3个月内）

    private Integer orderQuantity;  //起订量

    private String deliveryDate;  //交货期

    private String deliveryPlace;  //交货地点

    private String payMethod;  //支付方式
}

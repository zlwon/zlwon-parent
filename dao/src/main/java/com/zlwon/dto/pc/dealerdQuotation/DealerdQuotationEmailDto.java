package com.zlwon.dto.pc.dealerdQuotation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 我要询价，发送邮件入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class DealerdQuotationEmailDto {

	private  Integer  specId;//物性规格ID
	
	private String color;  //颜色/色号
	
	private Integer count;  //数量
	
	private  String  username;//用户姓名
	
	private  String  email;//用户邮箱
	
	private  String  phone;//用户电话
	
	private  String  company;//用户公司名称
	
	private  String  content;//查询/意见内容
}

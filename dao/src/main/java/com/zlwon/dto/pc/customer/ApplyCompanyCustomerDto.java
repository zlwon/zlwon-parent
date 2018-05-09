package com.zlwon.dto.pc.customer;

import lombok.Data;

/**
 * pc端用户申请成为企业用户入参
 * @author yuand
 *
 */
@Data
public class ApplyCompanyCustomerDto {

	private  String   companyShortName;//企业简称
	
	private  String   companyFullName;//企业全称
	
	private  String  charter;//营业执照
	
	private  String  intro;//企业介绍
	
	private  String  linkPerson;//企业联系人
	
	private  String  linkEmail;//企业联系人邮箱
	
	private  String  linkTel;//企业联系人电话
	
}

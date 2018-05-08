package com.zlwon.vo.pc.customer;

import lombok.Data;

/**
 * 企业全称信息出参
 * @author yuand
 *
 */
@Data
public class ApplyCompanyCustomerVo {

	private  String   name;//企业名称
	
	private  String  charter;//营业执照
	
	private  String  intro;//企业介绍
	
	private  String  linkPerson;//企业联系人
	
	private  String  linkEmail;//企业联系人邮箱
	
	private  String  linkTel;//企业联系人电话
	
	
	
}

package com.zlwon.dto.pc.dealerProduct;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 发送经销商邮件入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class SendDealerProductToSysDto {

	private String name;  //姓名
	
	private String mail;  //电邮
	
	private String mobile;  //电话
	
	private String company;  //公司名称
	
	private String advice;  //查询/意见
	
	private Integer specId;  //物性ID
	
	private String dealerProduct;  //经销商名称
}

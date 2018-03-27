package com.zlwon.dto.customer;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 更新用户入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class UpdateCustomerDto implements Serializable {

	private String mail;  //邮箱
	
	private String companyName;  //公司名称
	
	private String entryKey;  //微信加密字符串
}

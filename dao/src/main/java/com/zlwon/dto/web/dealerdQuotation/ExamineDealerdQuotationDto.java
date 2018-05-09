package com.zlwon.dto.web.dealerdQuotation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * web端审核材料报价单入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ExamineDealerdQuotationDto {

	private Integer id;  //材料报价单ID
	
	private Integer examine;  //审核状态 1：通过 2：驳回
	
	private String reason;  //审核理由
}

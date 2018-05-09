package com.zlwon.dto.web.dealerdQuotation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * web端分页全部材料报价单入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryAllDealerdQuotationPageDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
}

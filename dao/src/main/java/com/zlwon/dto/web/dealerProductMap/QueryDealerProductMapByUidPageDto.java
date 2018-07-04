package com.zlwon.dto.web.dealerProductMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * web端分页查询经销商可供产品入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryDealerProductMapByUidPageDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer userId;  //用户ID
}

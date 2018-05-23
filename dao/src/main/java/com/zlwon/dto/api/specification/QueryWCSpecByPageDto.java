package com.zlwon.dto.api.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小程序端分页查询物性表入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryWCSpecByPageDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private String searchText;  //搜索关键词
}

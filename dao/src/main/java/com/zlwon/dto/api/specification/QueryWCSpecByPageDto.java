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
	
	private Integer manufacturerId;  //生产商ID
	
	private Integer baseMaterialId;  //基材ID
	
	private Integer fillerId;  //填充物ID
	
	private Integer safeCertifyId;  //安规认证ID
	
	private String searchText;  //搜索关键词
	
	private Integer userId;  //当前用户ID，前端不传
}

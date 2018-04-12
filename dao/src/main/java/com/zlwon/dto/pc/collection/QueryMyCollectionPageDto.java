package com.zlwon.dto.pc.collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端查询我的所有收藏（可指定类型）分页入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryMyCollectionPageDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer type;  //信息类型，1物性表，2案例，3提问
	
	private Integer userId;  //用户ID（前端不传）
}

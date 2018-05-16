package com.zlwon.dto.pc.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端分页查询资讯信息入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryPcInfoByPageDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
}

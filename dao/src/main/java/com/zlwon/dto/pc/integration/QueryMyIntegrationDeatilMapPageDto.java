package com.zlwon.dto.pc.integration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端分页查询当前用户异动明细流水记录入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryMyIntegrationDeatilMapPageDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer userId;  //用户ID(前端不传)
}

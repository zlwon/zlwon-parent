package com.zlwon.dto.pc.processAdvice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端分页查询我的加工建议入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryMyProcessAdviceByPageDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer userId;  //用户ID（前端不传）
}

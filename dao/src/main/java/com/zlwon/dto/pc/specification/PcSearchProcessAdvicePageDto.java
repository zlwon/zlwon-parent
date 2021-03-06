package com.zlwon.dto.pc.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端分页查询物性表加工建议信息入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class PcSearchProcessAdvicePageDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer specId;  //物性表ID
}

package com.zlwon.dto.pc.specification;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端分页查询物性表经销商报价信息入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class PcSearchSpecDealerPageDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer specId;  //物性表ID
}

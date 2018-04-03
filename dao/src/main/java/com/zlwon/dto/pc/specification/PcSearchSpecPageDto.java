package com.zlwon.dto.pc.specification;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端分页查询物性表信息入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class PcSearchSpecPageDto implements Serializable {

	private String manufacturerStr;  //生产商字符串
	
	private String brandNameStr;  //商标字符串
	
	private String baseMaterialStr;  //基材字符串
	
	private String searchText;  //填写搜索栏字符串
	
	private Integer userId;  //当前用户ID，前端不传
	
	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
}

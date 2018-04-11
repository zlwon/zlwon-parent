package com.zlwon.dto.pc.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端分页查询物性表属性数据信息入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class PcSearchAttributeDataPageDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer specId;  //物性ID
	
	private Integer className;  //分类：物理特性Physical、机械强度Mechanical、冲击强度Impact、热学性能Thermal、阻燃特性Flammability、电气特性Electrical、光学参数Optical
}

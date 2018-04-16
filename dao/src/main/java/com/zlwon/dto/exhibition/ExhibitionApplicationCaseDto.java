package com.zlwon.dto.exhibition;

import lombok.Data;

/**
 * 展会案例列表筛选条件
 * @author yuand
 *
 */
@Data
public class ExhibitionApplicationCaseDto {

	private   Integer  id;//展会id
	
	private   String   key;//关键字查询
	
	private   Integer  mid;//材料生产商id
	
	private   Integer  industryId;//应用行业id
	
	private   Integer  marketId;//应用市场id
	
}

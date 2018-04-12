package com.zlwon.dto.pc.applicationCase;

import lombok.Data;

/**
 * pc官网，查询所有案例条件查询
 * @author yuand
 *
 */
@Data
public class QueryApplicationCaseListDto {

	private  Integer[]  mids;//生产商id数组
	
	private  Integer[]  tids;//商标id数组
	
	private  Integer[]  msid;//基材id数组
	
	private  Integer[]  industryIds;//应用行业id数组
	
	private  String   key;//关键字
	
}

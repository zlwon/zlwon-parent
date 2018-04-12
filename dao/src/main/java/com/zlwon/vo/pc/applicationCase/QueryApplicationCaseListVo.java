package com.zlwon.vo.pc.applicationCase;

import lombok.Data;

/**
 * pc端案例列表页面
 * @author yuand
 *
 */
@Data
public class QueryApplicationCaseListVo {

	private  Integer  id;//案例id
	
	private  String   title;//案例标题
	
	private  String   photo;//缩略图
	
	private  String   terminal;//终端客户名称
	
	private  String   name;//物性规格名称
	
	private  Integer  collect;//用户是否已收藏该案例0未收藏1已收藏
	
}

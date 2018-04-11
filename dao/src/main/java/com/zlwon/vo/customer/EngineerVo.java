package com.zlwon.vo.customer;

import lombok.Data;

/**
 * 工程师出餐(高级知料师)
 * @author yuand
 *
 */
@Data
public class EngineerVo {

	private  Integer  id;//工程师id，customer知料师的id
	
	private  String  nickname;//昵称
	
	private String  name;//真实姓名
	
	private String  company;//公司名称
	
	private  Integer  relevance;//展会案例是否关联工程师0未关联1关联
	
}

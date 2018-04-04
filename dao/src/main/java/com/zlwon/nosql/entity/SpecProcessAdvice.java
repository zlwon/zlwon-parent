package com.zlwon.nosql.entity;

import lombok.Data;

/**
 * mongodb物性表加工建议
 * @author yangy
 *
 */

@Data
public class SpecProcessAdvice {

	private String title;  //主题
	
	private String value;  //值
	
	private String unit;  //单位
}

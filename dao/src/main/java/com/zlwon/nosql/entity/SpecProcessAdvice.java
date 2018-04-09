package com.zlwon.nosql.entity;

import lombok.Data;

/**
 * mongodb物性表加工建议
 * @author yangy
 * yuand已用，如果修改请告知
 *
 */

@Data
public class SpecProcessAdvice {

	private String title;  //主题
	
	private String value;  //值
	
	private String unit;  //单位
}

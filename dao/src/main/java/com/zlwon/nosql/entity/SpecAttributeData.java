package com.zlwon.nosql.entity;

import java.util.List;

import lombok.Data;

/**
 * mongodb物性表属性数据
 * @author yangy
 * yuand已用，如果修改请告知
 */

@Data
public class SpecAttributeData {

	private String class_name;  //分类
	
	private String name;  //属性名
	
	private String status;  //干湿态
	
	private String unit;  //单位
	
	private String test_conditions;  //测试条件
	
	private String test_method;  //测试方法
}

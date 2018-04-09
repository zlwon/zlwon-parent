package com.zlwon.dto.specification;

import com.zlwon.nosql.entity.SpecificationData;
import com.zlwon.rdb.entity.Specification;

import lombok.Data;

@Data
public class SpecificationDto {

	//mogon需要的数据
	private String manufacturer;  //生产商
	
	private String trademark;  //商标
	
	private String material_science;  //基材
	
	private String filler;  //填充物
	
	private String flame_retardant;  //阻燃等级
	
	private String safety_certification;  //安规认证
	
	private Integer[] processingAdviceIds;//加工建议id数组
	
	private Integer[] attributeDataIds;//物性属性数据id数组
	
	//物性表需要的数据
	private String name;  //规格名称
	
	private Integer mid;  //生产商ID
	
	private Integer tid;  //商标ID
	
	private Integer msid;  //基材ID
	
	private Integer fid;  //填充物ID
	
	private String fillerRatio;  //填充比例
	
	private Integer flid;  //阻燃等级ID
	
	private Integer scid;  //安规认证
	
	private String label;  //规格名称模糊搜索用标签
	
	private String content;  //描述
	
	private String pdf;  //PDF文件
}

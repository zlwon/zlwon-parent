package com.zlwon.nosql.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * mongodb物性数据表实体
 * @author yangy
 * yuand已用，如果修改请告知
 */

@Data
@Document(collection="zl_specification_data")
public class SpecificationData {

	@Id
	private String id;  //mongodb ID
	
	private String name;  //规格名称
	
	private String manufacturer;  //生产商
	
	private String trademark;  //商标
	
	private String material_science;  //基材
	
	private String filler;  //填充物
	
	private String flame_retardant;  //阻燃等级
	
	private String safety_certification;  //安规认证
	
	private List<SpecProcessAdvice> processing_advice;  //加工建议
	
	private List<SpecAttributeData> attribute_data;  //物性数据
}

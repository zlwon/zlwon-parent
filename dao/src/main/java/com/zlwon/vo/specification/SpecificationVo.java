package com.zlwon.vo.specification;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 物性详情信息vo，主要是获取到安规认证标签id，通过安规认证标签信息id获取
 * @author yuand
 *
 */
@Data
public class SpecificationVo {

	private Integer id;

	
	private String name;  //规格名称
	
	private Integer mid;  //生产商ID
	
	private Integer tid;  //商标ID
	
	private Integer msid;  //基材ID
	
	private String fidStr;  //填充物ID，多个用,隔开
	
	private String fillerRatio;  //填充比例
	
	private String scidStr;  //安规认证标签详情id，多个用,隔开
	private Integer scid; //安规认证标签id，前端需要，该vo就是映射该字段
	
	private String label;  //规格名称模糊搜索用标签
	
	private String content;  //描述
	
	private String pdf;  //PDF文件
	
	
}

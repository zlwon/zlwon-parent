package com.zlwon.vo.applicationCase;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 后端得到所有案例显示的数据
 * @author yuand
 *
 */
@Data
public class ApplicationCaseListVo {

	private  Integer  id;//案例id
	
	private  String  title;//案例标题
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private  Date  createTime;//案例创建时间
	
	private  String  appProductName;//应用产品
	
	private  String  specificationName;//规格名称
	
	private  String  company;//生产商
	
	private  String  industryName;//应用行业
	
	private  String  markerName;//应用市场
	
	private  String  baseMaterial;//基材
	
	private  Integer  hot;//该案例是否热门展示0否1是
}

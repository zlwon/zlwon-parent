package com.zlwon.vo.exhibitionCaseMap;

import java.io.Serializable;

import lombok.Data;

/**
 * 后端展会显示所有案例数据(会标识是否已关联)
 * @author yuand
 *
 */
@Data
public class ExhibitionCaseMapVo{

	
	private  String  title;//案例标题
	
	private  String  specificationName;//规格名称
	
	private  String  company;//生产商
	
	private  String  industryName;//应用行业
	
	private  String  markerName;//应用市场
	
	private  String  baseMaterial;//基材
	
	private  Integer  eid;//展会id
	
	private  Integer  aid;//案例id
	
	private  Integer  relevance;//展会是否已关联案例0未关联1已关联
}

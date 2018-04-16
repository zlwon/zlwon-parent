package com.zlwon.vo.exhibitionCaseMap;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 后端展会显示所有案例数据(会标识是否已关联)
 * @author yuand
 *
 */
@Data
public class ExhibitionCaseMapVo{

	
	private  String  title;//案例标题
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private  Date  createTime;//案例创建时间
	
	private  String  appProductName;//应用产品
	
	private  String  specificationName;//规格名称
	
	private  Integer  eid;//展会id
	
	private  Integer  aid;//案例id
	
	private  Integer  relevance;//展会是否已关联案例0未关联1已关联
	
	private  Integer  relevanceCustomere;//展会案例是否关联工程师
	
	private  String   nickname;//关联工程师的昵称
}

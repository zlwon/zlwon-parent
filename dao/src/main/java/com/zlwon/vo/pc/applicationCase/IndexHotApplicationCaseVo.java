package com.zlwon.vo.pc.applicationCase;

import java.util.List;

import lombok.Data;

/**
 * pc首页热门案例展示
 * @author yuand
 *
 */
@Data
public class IndexHotApplicationCaseVo {

	private   Integer  id;//案例id
	
	private   String   title;//案例标题
	
	private   String   photo;//案例缩略图
	
	private   String   name;//物性规格名称
	
	private   List<IndexHotApplicationCaseQuestionAndAnswerVo>   questionAndAnswerVo;//案例对应的问答信息集合
}

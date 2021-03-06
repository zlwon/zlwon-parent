package com.zlwon.dto.pc.questions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端我回答的问题分页入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryMyAnswerQuestionsDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	//private Integer infoId;  //信息ID
	
	private Integer infoClass;  //信息类别：1:物性、2:案例
	
	private Integer userId;  //用户ID（前端不传）
}

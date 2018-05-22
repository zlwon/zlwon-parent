package com.zlwon.dto.api.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端查询特定类型的问题（可指定具体）分页入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryDefineClearQuestionsDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer infoId;  //信息ID
	
	private Integer infoClass;  //信息类别：1:物性、2:案例
	
	private Integer moduleType;  //模块类型
	
	private Integer userId;  //用户ID
}

package com.zlwon.dto.api.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小程序端根据问题ID查询分页回答入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryAnswerByQuestionIdWCDto {

	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private Integer questionId;  //问题ID
	
	private Integer userId;  //用户ID（前端不传）
}

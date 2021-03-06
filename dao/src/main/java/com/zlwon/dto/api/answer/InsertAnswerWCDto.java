package com.zlwon.dto.api.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小程序端新增回答入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class InsertAnswerWCDto {

	private Integer questionId;  //问题ID
	
	private String content;  //回答内容
	
	private Integer isAnonymous;  //是否匿名  0：非匿名 1：匿名
}

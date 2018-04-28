package com.zlwon.dto.pc.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端编辑提问回答入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class UpdateAnswerPcDto {

	private Integer answerId;  //回答ID
	
	private String content;  //回答内容
}

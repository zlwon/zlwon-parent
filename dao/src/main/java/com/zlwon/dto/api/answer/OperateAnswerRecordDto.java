package com.zlwon.dto.api.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小程序端新增/删除回答点赞记录入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class OperateAnswerRecordDto {

	private Integer answerId;  //回答ID
}

package com.zlwon.dto.api.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小程序端根据信息ID查询提问列表入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryQuestionListByInfoIdDto {

	private Integer infoId;  //信息ID
	
	private Integer infoClass;  //信息类别：1:物性、2:案例
	
	private Integer userId;  //用户ID
}

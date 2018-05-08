package com.zlwon.dto.pc.questions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端新增提问入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class InsertQuestionsDto {

	private Integer infoId;  //信息ID
	
	private Integer infoClass;  //信息类别：1:物性、2:案例
	
	private Integer moduleType;  //模块类型
	
	private String title;  //提问标题
	
	private String content;  //问题内容
	
	private String inviteUser;  //邀请用户（最多三个，可不传）
}

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
public class SendInviteByQuestionsIdDto {

	private Integer questionId;  //问题ID
	
	private String inviteUser;  //邀请用户（最多三个，可不传）
}

package com.zlwon.vo.pc.answer;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 推荐回答邀请用户出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class InvitateAnswerDetailVo {

	private Integer uid;  //用户ID
	
	private String nickname;  //用户昵称
	
	private String headerimg;  //用户头像
	
	private Integer lev;  //用户等级
	
	private String recommendReason;  //推荐原因
}

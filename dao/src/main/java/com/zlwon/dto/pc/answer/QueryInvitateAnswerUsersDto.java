package com.zlwon.dto.pc.answer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * pc端查询邀请回答推荐用户入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class QueryInvitateAnswerUsersDto {

	private Integer infoId;  //信息ID
	
	private Integer type;  //类型 1：物性 2：案例
	
	private String userName;  //查询用户名称
	
	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
}

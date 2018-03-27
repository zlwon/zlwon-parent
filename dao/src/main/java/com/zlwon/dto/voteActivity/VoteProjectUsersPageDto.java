package com.zlwon.dto.voteActivity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 投票项目用户统计分页入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class VoteProjectUsersPageDto implements Serializable {
	
	private Integer projectId;  //投票项目ID
	
	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private String entryKey;  //微信加密字符串
}

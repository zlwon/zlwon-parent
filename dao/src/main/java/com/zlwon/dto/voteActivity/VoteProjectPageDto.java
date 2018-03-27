package com.zlwon.dto.voteActivity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 投票项目分页入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class VoteProjectPageDto implements Serializable {

	private Integer activityId;  //活动ID
	
	private Integer currentPage;  //当前页
	
	private Integer pageSize;  //每页显示的总条数
	
	private String entryKey;  //微信加密字符串
}

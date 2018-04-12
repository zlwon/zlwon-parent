package com.zlwon.dto.voteActivity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户投票-点赞入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AddVoteProjectRecordDto implements Serializable {

	private Integer activityId;  //活动ID
	
	private Integer projectId;  //项目ID
	
	private String nickName;  //用户昵称
	
	private String headerimg;  //头像
	
	private String entryKey;  //微信加密字符串
}

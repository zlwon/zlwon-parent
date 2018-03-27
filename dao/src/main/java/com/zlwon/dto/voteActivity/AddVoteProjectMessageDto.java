package com.zlwon.dto.voteActivity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户评论投票项目入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AddVoteProjectMessageDto implements Serializable {

	private Integer activityId;  //活动ID
	
	private Integer projectId;  //项目ID
	
	private String messageInfo;  //点评内容
	
	private String entryKey;  //微信加密字符串
}

package com.zlwon.vo.voteActivity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 投票项目用户统计出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class RecordUserInfoVo implements Serializable {

	private Integer iid;  //参与活动信息ID
	
	private Integer aid;  //活动ID
	
	private Integer uid;  //用户ID
	
	private String nickName;  //用户昵称
	
	private String headerimg;  //用户头像
	
	private Integer supportNums;  //点赞量
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //最新投票时间
}

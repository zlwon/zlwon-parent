package com.zlwon.vo.voteActivity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 投票活动参与项目信息点评记录详细出参
 * 该vo，yuand也使用了，如修改，望通知
 * @author yangy
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class VoteMessageDetailVo implements Serializable {

	private Integer id;  //自增ID
	
	private Integer uid;  //点评用户ID
	
	private String nickName;  //点评用户昵称
	
	private String headerimg;  //点评用户头像
	
	private Integer iid;  //参与活动信息ID
	
	private Integer aid;  //活动ID
	
	private String messageInfo;  //点评内容
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;  //创建时间
}

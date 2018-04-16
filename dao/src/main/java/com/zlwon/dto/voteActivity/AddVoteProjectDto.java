package com.zlwon.dto.voteActivity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 新增投票项目入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AddVoteProjectDto implements Serializable {

	private Integer aid;  //活动表ID
	
	private String photo;  //图片
	
	private String title;  //信息标题
	
	private String entryKey;  //微信加密字符串
	
	private Integer fileType;  //文件类型  1：图片  2：视频
	
	private String nickName;  //用户昵称
	
	private String headerimg;  //头像
}

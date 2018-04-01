package com.zlwon.dto.voteActivity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 新增投票项目入参(包含上传)
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AddVoteProjectUploadDto implements Serializable {

	private Integer aid;  //活动表ID
	
	private String fileUrl;  //虚拟文件地址
	
	private String title;  //信息标题
	
	private Integer fileType;  //文件类型  1：图片  2：视频
	
	private String fileFormat;  //文件后缀
	
	private String entryKey;  //微信加密字符串
}

package com.zlwon.vo.voteActivity;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 投票项目上传文件出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class FileClassInfoVo {

	private String bigPicUrl;  //大图路径
	
	private String smallPicUrl;  //压缩图返回路径
}

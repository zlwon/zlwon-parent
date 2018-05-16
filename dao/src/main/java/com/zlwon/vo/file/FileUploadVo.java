package com.zlwon.vo.file;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 文件上传出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class FileUploadVo {

	private String mappingUrl;  //映射地址
	
	private String storeUrl;  //存储地址
	
	private String fileName;  //文件名称
	
	private String fileType;  //文件类型
	
	private String thumbPicUrl;  //压缩图片地址（仅限图片）
}

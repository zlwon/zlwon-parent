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
public class FileUploadVo implements Serializable {

	private String mappingUrl;  //映射地址
	
	private String storeUrl;  //存储地址
}

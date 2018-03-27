package com.zlwon.dto.scanCard;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 扫描腾讯云OCR入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ScanTencentOcrDto implements Serializable {

	private String appid;  //项目ID
	
	private String bucket;  //图片空间
	
	private Integer retImage;  //0 不返回图片，1 返回图片
	
	private String[] urlList;  //图片 url 列表
}

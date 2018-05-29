package com.zlwon.dto.web.upload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * web端上传二进制文件入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class UploadBinaryFileDto {

	private byte[] picByte;  //二进制图片字节
}

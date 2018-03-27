package com.zlwon.dto.scanCard;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 扫描名片全能王OCR入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ScanCamCardOcrDto implements Serializable  {

	private String filePath;  //图片地址
}

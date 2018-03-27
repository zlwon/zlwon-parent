package com.zlwon.vo.scanCard;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 扫描信息粗略信息出参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class ScanCardSketchyVo implements Serializable {

	private String mobile;  //手机号码
	
	private String mail;  //邮箱
	
	private String remark;  //备注字段
}

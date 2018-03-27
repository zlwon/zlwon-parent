package com.zlwon.dto.programAccess;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 新增小程序用户访问浏览行为记录入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class AddProgramAccessRecordDto implements Serializable {

	private String route;  //页面路由
	
	private String entryKey;  //微信加密字符串
	
	private String remark;  //备注
}

package com.zlwon.dto.manage;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 扫描获取数据注册参数入参
 * @author yangy
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class RegisterScanDto implements Serializable {

	private String mobile;  //手机号码
	
	private String mail;  //邮箱
	
	private String cardUrl;  //名片地址
	
	private String remark;  //备注字段
	
	private String entryKey;  //微信加密字符串
	
	private String nickName;  //昵称
	
	private String headerimg;  //用户头像
}

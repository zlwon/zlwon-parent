package com.zlwon.dto.pc.user;

import lombok.Data;

/**
 * 用户登录参数
 * 登录，修改密码
 * @author yuand
 *
 */
@Data
public class UserLoginDto {

	private  String   name;//可以是手机号，也可以是邮箱
	private  String   password;//密码
	private  String   newPassword;//新密码密码
	
}

package com.zlwon.server.service;

import com.github.qcloudsms.SmsSingleSenderResult;

/**
 * 短信ServiceImpl
 * 腾讯云接口
 * @author yangy
 *
 */

public interface MobileMessageService {

	/**
	 * 发送短信验证码
	 * @param mobile  手机号（不带国家码）
	 * @return
	 */
	public SmsSingleSenderResult sendCodeMessage(String mobile);
	
	/**
	 * 注册成功发送用户默认密码到用户邮箱
	 * @param mobile  手机号（不带国家码）
	 * @return
	 */
	public SmsSingleSenderResult sendRegisterPasswordMessage(String mobile);
}

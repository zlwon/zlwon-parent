package com.zlwon.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.pc.user.UserLoginDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.CustomerMapper;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.server.service.RedisService;
import com.zlwon.server.service.SystemService;
import com.zlwon.sms.SmsSender;
import com.zlwon.utils.CookieUtils;
import com.zlwon.utils.CustomerUtil;
import com.zlwon.utils.JsonUtils;
import com.zlwon.utils.MD5Utils;

/**
 * 用于pc用户登录，注册，找回密码等操作
 * 
 * @author yuand
 *
 */
@Service
public class SystemServiceImpl implements SystemService {

	@Value("${pc.user.header}")
	private String token;
	@Value("${pc.cookie.name}")
	private String cookieName;
	@Value("${pc.redis.user.token.prefix}")
	private String tokenPrefix;
	@Value("${pc.redis.user.token.field}")
	private String tokenField;
	@Value("${pc.redis.user.token.make}")
	private String tokenMake;
	@Value("${pc.redis.user.token.expiredTime}")
	private String expiredTime;

	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private RedisService redisService;

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param loginDto
	 * @return 返回token
	 */
	public String userLogin(HttpServletRequest request, HttpServletResponse response, UserLoginDto loginDto) {
		// 根据手机号或者邮箱找到用户信息
		Customer customer = customerMapper.selectCustomerByMobileOrEmail(loginDto.getName().trim());
		if (customer == null || !customer.getPassword().equals(MD5Utils.encode(loginDto.getPassword()))) {
			throw new CommonException(StatusCode.PASSWORD_INVALID);
		}
		String cookieValue = UUID.randomUUID().toString().replace("-", "");
		String token = getToken(customer);
		// 保存登录用户信息到redis
		redisService.hSet(tokenPrefix + token, tokenField, JsonUtils.objectToJson(customer));
		redisService.expire(tokenPrefix + token, Integer.valueOf(expiredTime), TimeUnit.MINUTES);
		redisService.hSet(tokenPrefix + token, tokenMake, cookieValue);
		CookieUtils.setCookie(request, response, cookieName, cookieValue);

		return token;
	}

	/**
	 * 用户修改密码
	 */
	public int alterPassword(UserLoginDto userLoginDto, HttpServletRequest request) {
		// 判断原密码是否正确
		Customer record = CustomerUtil.getCustomer2Redis(tokenPrefix + request.getHeader(token), tokenField,
				redisService);
		if (!(record.getPassword()).equals(MD5Utils.encode(userLoginDto.getPassword()))) {
			throw new CommonException(StatusCode.OLD_PASSWORD_INVALID);
		}
		Customer customer = new Customer();
		customer.setId(record.getId());
		customer.setPassword(MD5Utils.encode(userLoginDto.getNewPassword()));
		return customerMapper.updateByPrimaryKeySelective(customer);
	}

	/**
	 * 通过短信验证码修改登录密码
	 */
	public int alterPassword(String mobile, String code, String password) {
		String recode = redisService.get("mobilecode_" + mobile);
		if (StringUtils.isBlank(recode) || !recode.equals(code)) {
			throw new CommonException(
					StringUtils.isBlank(recode) ? StatusCode.ACTIVE_CODE_EXPIRED : StatusCode.ACTIVE_CODE_INVALID);
		}
		redisService.delete("mobilecode_" + mobile);
		// 根据手机号查找到该用户，查看该用户是否存在
		Customer customer = customerMapper.selectCustomerByMobile(mobile);
		if (customer == null) {
			throw new CommonException(StatusCode.USER_NOT_EXIST);
		}
		customer.setPassword(MD5Utils.encode(password));
		return customerMapper.updateByPrimaryKeySelective(customer);
	}

	/**
	 * 重置密码-发送验证码，,判断手机号是否存在数据库
	 * 
	 * @param mobile
	 * @return
	 */
	public SmsSingleSenderResult sendCodeMessage(String mobile) {
		// 根据手机号查找到该用户，查看该用户是否存在
		Customer customer = customerMapper.selectCustomerByMobile(mobile);
		if (customer == null) {
			throw new CommonException(StatusCode.USER_NOT_EXIST);
		}
		SmsSingleSenderResult result = null;

		try {
			// 生成随机短信验证码
			String mobile_code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

			// 发送短信验证码
			SmsSender sender = new SmsSender();
			ArrayList<String> params = new ArrayList<String>();
			params.add(mobile_code);
			params.add("10");
			result = sender.sendSmsWithTemplate("86", mobile, 93306, params, "", "", "");

			// 将短信验证码存储入redis，命名规则mobilecode_+对应mobile符串
			// 短信验证码10分钟内有效
			redisService.set("mobilecode_" + mobile, mobile_code, 60 * 10, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private String getToken(Customer customer) {
		return MD5Utils.encode(customer.getId() + "");
	}

}

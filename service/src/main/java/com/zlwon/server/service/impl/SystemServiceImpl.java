package com.zlwon.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Value("${api.user.header}")
	private String openid;
	
	//redis短信验证码key前缀
	private final String MOBILECODE = "mobilecode_";
	
	//小程序注册默认密码
	private final String DEFAULTPASSWORD = "666666";

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
	 * 注销
	 */
	@Override
	public void userLogout(HttpServletRequest request,HttpServletResponse  response) {
		redisService.delete(tokenPrefix + request.getHeader(token));
		CookieUtils.deleteCookie(request, response, cookieName);
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
		// 根据手机号查找到该用户，查看该用户是否存在
		Customer customer = customerMapper.selectCustomerByMobile(mobile);
		if (customer == null) {
			throw new CommonException(StatusCode.USER_NOT_EXIST);
		}
		customer.setPassword(MD5Utils.encode(password));
		redisService.delete("mobilecode_" + mobile);
		return customerMapper.updateByPrimaryKeySelective(customer);
	}

	/**
	 * 发送手机验证码,根据类型判断手机号是否存在数据库
	 * @param mobile
	 * @param type 0重置密码1注册
	 * @return
	 */
	public SmsSingleSenderResult sendCodeMessage(String mobile,Integer  type) {
		// 根据手机号查找到该用户，查看该用户是否存在
		Customer customer = customerMapper.selectCustomerByMobile(mobile);
		if(type == 0){
			if (customer == null) {
				throw new CommonException(StatusCode.USER_NOT_EXIST);
			}
		}else if (type == 1) {
			if (customer != null) {
				throw new CommonException(StatusCode.MOBILE_IS_REGISTER);
			}
		}else {
			throw new CommonException(StatusCode.INVALID_PARAM);
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
	
	/**
	 * 发送手机验证码，不做任何判断
	 * @param mobile
	 * @return
	 */
	@Override
	public SmsSingleSenderResult sendCodeMessage(String mobile) {
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
			redisService.set(MOBILECODE + mobile, mobile_code, 60 * 10, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			result = new  SmsSingleSenderResult();
		}
		return  result;
	}
	
	/**
	 * 根据用户手机号和验证码执行登录(注册)，小程序端
	 * 	1 openid和手机号不存在
	 *		添加用户，保存redis
	 *
	 * 	2 手机号存在
	 * 		数据库openid未绑定:需要把用户绑定openid，保存redis
	 * 		数据库openid和得到openid不一样，抛出异常
	 * 		数据库openid和得到openid一样，直接保存到redis
	 * 
	 * redis:openid作为key，用户信息作为value，过期时间100天
	 * @param mobile 手机号
	 * @param code 验证码
	 * @param nickName 昵称
	 * @param headerimg 头像
	 * @return
	 */
	@Transactional
	@Override
	public void wxLogin(HttpServletRequest request, String mobile, String code,String nickName,String headerimg) {
		Customer  redisCustomer = null;
		
		String recode = redisService.get(MOBILECODE + mobile);
		if (StringUtils.isBlank(recode) || !recode.equals(code)) {
			throw new CommonException(
					StringUtils.isBlank(recode) ? StatusCode.ACTIVE_CODE_EXPIRED : StatusCode.ACTIVE_CODE_INVALID);
		}
		//得到用户openid
		String openId = CustomerUtil.getOpenid(request, openid);
		//根据手机号，得到用户信息
		Customer customerByPhone = customerMapper.selectCustomerByMobile(mobile);
		//根据openid，得到用户信息
		Customer customerByOpenId = customerMapper.selectCustomerByOpenId(openId);
		
		if(customerByPhone == null){//手机号用户不存在
			redisCustomer = matchMobile(customerByOpenId, mobile, openId,nickName,headerimg);
		}else {//手机号用户存在
			redisCustomer = matchOpenid(customerByPhone, customerByOpenId, openId);
		}
		
		//redis删除该验证码
		redisService.delete(MOBILECODE + mobile);
		
		//把用户信息保存到redis中，openid作为key，用户信息作为value,100天过期
		redisService.set(openId, JsonUtils.objectToJson(redisCustomer),100,TimeUnit.DAYS);
	}
	
	
	/**
	 * 查看用户是否登录（小程序端）
	 * @param request
	 * @return
	 */
	@Override
	public void wxIsLogin(HttpServletRequest request) {
		String openId = CustomerUtil.getOpenid(request, openid);
		String val = redisService.get(openId);
		if(StringUtils.isBlank(val)){
			throw  new  CommonException(StatusCode.MANAGER_CODE_NOLOGIN);
		}
	}

	//手机号用户不存在，匹配
	private  Customer   matchMobile(Customer  customerByOpenId,String  mobile,String openId,String nickName,String headerimg){
		//openid用户存在
		if(customerByOpenId != null){
			//如果openid用户手机号未绑定，修改为目前手机号
			if(StringUtils.isBlank(customerByOpenId.getMobile())){
				customerByOpenId.setMobile(mobile);
				customerMapper.updateByPrimaryKeySelective(customerByOpenId);
			}else if(!customerByOpenId.getMobile().equals(mobile)){//openid用户手机号和目前手机号不一样,抛出异常，不能登录
				throw  new  CommonException(StatusCode.WX_LOGIN_NOT_MATCH);
			}
			return  customerByOpenId;
			
		}else {//openid用户不存在，保存用户信息
			Customer customer = new  Customer();
			Date  date = new  Date();
			customer.setCreateTime(date);
			customer.setPassword(MD5Utils.encode(DEFAULTPASSWORD));
			customer.setRole(0);//设置用户类型为普通用户 
			String randomStr = String.valueOf((int)((Math.random()*9+1)*10));
			customer.setNickname(StringUtils.isNotBlank(nickName)?nickName:"知料用户"+customer.getMobile().substring(3)+randomStr);
			customer.setOpenid(openId);
			customer.setMobile(mobile);
			customer.setHeaderimg(headerimg);
			customerMapper.insertSelective(customer);
			return  customer;
		}
	}
	
	//手机号用户存在时，匹配
	private  Customer   matchOpenid(Customer customerByPhone,Customer customerByOpenId,String  openId){
		//手机号用户的openid和传入的openid是否一样，不一样抛出异常
		if(StringUtils.isNotBlank(customerByPhone.getOpenid()) && !customerByPhone.getOpenid().equals(openId)){
			throw  new  CommonException(StatusCode.WX_LOGIN_NOT_MATCH);
		}
		
		//openid用户不存在,把openid绑定到手机号用户上
		if(customerByOpenId == null){
			customerByPhone.setOpenid(openId);
			customerMapper.updateByPrimaryKeySelective(customerByPhone);
			
		}else {//openid用户存在，需要判断他俩是否是同一个对象，如果不是，抛出异常
			if(!customerByPhone.getId().equals(customerByOpenId.getId())){
				throw  new  CommonException(StatusCode.WX_LOGIN_NOT_MATCH);
			}
		}
		return  customerByPhone;
	}

	/**
	 * pc生成token
	 * @param customer
	 * @return
	 */
	private String getToken(Customer customer) {
		return MD5Utils.encode(customer.getId() + "");
	}
	
	

}

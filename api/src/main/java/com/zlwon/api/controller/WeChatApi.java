package com.zlwon.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.weChat.LoginCodeDto;
import com.zlwon.rdb.entity.Customer;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.CustomerService;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.HttpUtils;
import com.zlwon.vo.weChat.RequestOpenIdByLoginCodeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 微信相关api
 * @author yangy
 *
 */

@Api
@RestController
@RequestMapping("/api/weChat")
public class WeChatApi {

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 根据微信登录凭证获取session_key和openid
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "根据微信登录凭证获取session_key和openid")
    @RequestMapping(value = "/requestOpenIdByLoginCode", method = RequestMethod.POST)
    public ResultData requestOpenIdByLoginCode(LoginCodeDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String appid = form.getAppid();  //小程序唯一标识
		String secret = form.getSecret();  //小程序的 app secret
		String js_code = form.getJs_code();  //登录时获取的 code
		String grant_type = form.getGrant_type();  //填写为 authorization_code
		String entryKey = form.getEntryKey();  //加密字符串
		
		if(StringUtils.isBlank(appid) || StringUtils.isBlank(secret) || StringUtils.isBlank(js_code) || StringUtils.isBlank(grant_type)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//接口地址
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+js_code+"&grant_type="+grant_type;
		String openId = "";
		RequestOpenIdByLoginCodeVo result = new RequestOpenIdByLoginCodeVo();
		
		if(StringUtils.isBlank(entryKey)){  //加密字符串不为空
			//获取openId
			openId = getopenId(requestUrl);
		}else{
			//从redis中获取openId
			String mdStr = redisService.get("encryKey_"+entryKey);
			if(StringUtils.isBlank(mdStr)){
				//获取openId
				openId = getopenId(requestUrl);
			}else{
				String[] arr = mdStr.split("_");
				openId = arr[0];
			}
		}
		
		if(StringUtils.isBlank(openId)){
			return ResultData.error(StatusCode.WECHAT_REQUEST_ERROR);
		}
		
		//根据openId获取用户信息
		Customer userinfo = customerService.selectCustomerByOpenId(openId);
		if(userinfo == null){
			result.setIsExist(0);
		}else{
			result.setIsExist(1);
		}
		
		String encryKey = openId+"_"+UUID.randomUUID();
		result.setEntryKey(encryKey);
		
		//将key存储进入redis,设置存储时间为30天
		redisService.set("encryKey_"+encryKey, encryKey, 30 , TimeUnit.DAYS);
		
		return ResultData.one(result);
	}

	/**
	 * 获取openId
	 * @param requestUrl
	 * @return
	 */
	public static String getopenId(String requestUrl) {
		String encryKey = ""; 
		
		//获取请求信息
		JSONObject jsonObject = HttpUtils.httpsRequest(requestUrl, "GET", null);
		
		//判断接口调取成功还是失败
		if(jsonObject == null){
			return "";
		}else{
			if(jsonObject.containsKey("errcode")){
				return "";
			}else{
				String sessionKey = jsonObject.getString("session_key");
				String openId = jsonObject.getString("openid");
				if(StringUtils.isBlank(openId)){
					return "";
				}
	
				encryKey = openId;
				/*encryKey = openId+"_"+UUID.randomUUID();*/
			}
		}
		
		return encryKey;
	}
}

package com.zlwon.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.weChat.LoginCodeDto;
import com.zlwon.rest.ResultData;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
	
	/**
	 * 根据微信登录凭证获取session_key和openid
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "根据微信登录凭证获取session_key和openid")
    @RequestMapping(value = "/requestOpenIdByLoginCode", method = RequestMethod.POST)
    public ResultData replyConsultation(LoginCodeDto form){
		
		//验证参数
		if(form == null){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		String appid = form.getAppid();  //小程序唯一标识
		String secret = form.getSecret();  //小程序的 app secret
		String js_code = form.getJs_code();  //登录时获取的 code
		String grant_type = form.getGrant_type();  //填写为 authorization_code
		
		if(StringUtils.isBlank(appid) || StringUtils.isBlank(secret) || StringUtils.isBlank(js_code) || StringUtils.isBlank(grant_type)){
			return ResultData.error(StatusCode.INVALID_PARAM);
		}
		
		//接口地址
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+js_code+"&grant_type="+grant_type;
		
		//获取请求信息
		JSONObject jsonObject = HttpUtils.httpsRequest(requestUrl, "GET", null);
		
		//判断接口调取成功还是失败
		String encryKey = ""; 
		if(jsonObject == null){
			return ResultData.error(StatusCode.WECHAT_REQUEST_ERROR);
		}else{
			if(jsonObject.containsKey("errcode")){
				return ResultData.error(StatusCode.WECHAT_REQUEST_ERROR);
			}else{
				//result = JSON.parseObject(jsonObject.toJSONString(),LoginCodeRequestVo.class);
				String sessionKey = jsonObject.getString("session_key");
				String openId = jsonObject.getString("openid");
				
				//获取时间戳
				/*SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
				String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
*/				
				encryKey = openId+"_"+UUID.randomUUID();
				
				//将key存储进入redis
				redisService.set("encryKey_"+encryKey, encryKey);
			}
		}
		
		return ResultData.one(encryKey);
	}
}

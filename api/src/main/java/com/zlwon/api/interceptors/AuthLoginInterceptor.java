package com.zlwon.api.interceptors;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zlwon.api.annotations.AuthLogin;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.CookieUtils;


@Component
public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	@Value("${api.user.header}")
	private String openid;
	
	@Autowired
	private RedisService redisService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, token");
		System.out.println("拦截器进入");

		if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			return true;
		}
		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final Class<?> clazz = method.getDeclaringClass();
		// 得到前端请求头中传递的openid
		String  token = request.getHeader(openid);
		
		if(StringUtils.isNotBlank(token)){
			//redis设置过期时间
			redisService.expire(token, 100, TimeUnit.DAYS);
		}
		
		if (clazz.isAnnotationPresent(AuthLogin.class) || method.isAnnotationPresent(AuthLogin.class)) {

			// 如果请求头token参数为空，抛出未登录
			if (StringUtils.isBlank(token)) {
				throw new CommonException(StatusCode.MANAGER_CODE_NOLOGIN);
			} else {
				//根据请求头的token值，去redis查看是否存在用户信息
				String val = redisService.get(token);
				if(StringUtils.isBlank(val)){
					throw   new CommonException(StatusCode.MANAGER_CODE_NOLOGIN);
				}
			}
		}

		return true;
	}
}

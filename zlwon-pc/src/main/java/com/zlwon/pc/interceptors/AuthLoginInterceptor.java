package com.zlwon.pc.interceptors;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.pc.annotations.AuthLogin;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.CookieUtils;

@Component
public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	@Value("${pc.user.header}")
	private  String  tokenName;
	@Value("${pc.cookie.name}")
	private  String  cookieName;
	@Value("${pc.redis.user.token.prefix}")
	private  String  tokenPrefix;
	@Value("${pc.redis.user.token.make}")
	private  String  tokenMake;
	
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
		if (clazz.isAnnotationPresent(AuthLogin.class) || method.isAnnotationPresent(AuthLogin.class)) {

			// 得到前端请求头中传递的token
			String  token = request.getHeader(tokenName);
			// 如果请求头token参数为空，抛出未登录
			if (StringUtils.isBlank(token)) {
				throw new CommonException(StatusCode.MANAGER_CODE_NOLOGIN);
			} else {
				//得到cookie,判断是否过期
				String cookieValue = CookieUtils.getCookieValue(request, cookieName);
				if(StringUtils.isBlank(cookieValue)){
					throw  new  CommonException(StatusCode.MANAGER_CODE_NOLOGIN);
				}
				//根据请求头的token，去redis中查看是否和cookie值一样
				Object value = redisService.hGet(tokenPrefix+token, tokenMake);
				if(value == null || StringUtils.isBlank(value.toString()) || !value.equals(cookieValue)){
					throw new CommonException( value == null || StringUtils.isBlank( value.toString()) ? StatusCode.MANAGER_CODE_NOLOGIN:StatusCode.MANAGER_CODE_EXISTLOGIN);
				}

				return true;
			}
		}

		return true;
	}
}

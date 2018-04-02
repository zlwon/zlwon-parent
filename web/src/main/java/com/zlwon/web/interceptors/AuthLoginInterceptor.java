package com.zlwon.web.interceptors;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.server.config.WxApplicationConfig;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.QRCodeUtil;
import com.zlwon.web.annotations.AuthLogin;

@Component
public class AuthLoginInterceptor extends HandlerInterceptorAdapter{

	private static final  String   TOKEN = "token";
	@Value("${wx.token.redis.key}")
	private  String   ACCESS_TOKEN;
	@Autowired
	private  RedisService   redisService;
	@Autowired
    private  WxApplicationConfig  applicationConfig;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");  
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, token");
		System.out.println("拦截器进入");
		
		if(request.getRequestURI().indexOf("/addExhibitionApp") > 0){//执行展会关联工程师，需要生成二维码，要查看accessToken是否存在
			String accessToken = redisService.get(ACCESS_TOKEN);
			if(StringUtils.isBlank(accessToken)){
				//把accesstoken保存到redis中有效期100分钟，
				redisService.set(ACCESS_TOKEN, QRCodeUtil.getToken(applicationConfig.getAppid(), applicationConfig.getSecret()),100,TimeUnit.MINUTES);
			}
		}
		
		// if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
		// return true;
		// }
//		    final HandlerMethod handlerMethod = (HandlerMethod) handler; 
//		    final Method method = handlerMethod.getMethod(); 
//		    final Class<?> clazz = method.getDeclaringClass(); 
//		    if (clazz.isAnnotationPresent(AuthLogin.class) ||  method.isAnnotationPresent(AuthLogin.class)) { 
//		    	
//		    	//得到前端请求头中传递的参数，管理员的账号
//		        String  token = (String) request.getHeader(TOKEN);
//		        //如果参数为空，抛出参数错误异常
//		        if(StringUtils.isBlank(token)){ 
//		        	throw new CommonException(StatusCode.INVALID_PARAM);
//		        }else{ 
//		        	//得到session
//		        	HttpSession session = request.getSession();
//		        	//得到当前sessionID
//		        	String newSessionId = session.getId();
//		        	String oldSessionId = (String) session.getAttribute(token);
//		        	
//		        	if(StringUtils.isBlank(oldSessionId)){
//		        		//未登陆
//		        		throw new CommonException(StatusCode.MANAGER_CODE_NOLOGIN);
//		        	}
//		        	if(!oldSessionId.equals(newSessionId)){
//	        			//账号在别处登陆
//	        			throw new CommonException(StatusCode.MANAGER_CODE_EXISTLOGIN);
//	        		}
//		        	
//		        	return true; 
//		        } 
//		    } 
		  
		    return true; 
	}
}

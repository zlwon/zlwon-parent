package com.zlwon.web.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zlwon.web.config.ueditorConfig.ActionEnter;
import com.zlwon.web.config.ueditorConfig.ConfigManager;
import com.zlwon.web.config.ueditorConfig.UEditorConfig;
import com.zlwon.web.interceptors.AuthLoginInterceptor;

@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + uEditorConfig.getUploadPath());
        super.addResourceHandlers(registry);

    }
    
    
    @Autowired
    private  AuthLoginInterceptor  authLoginInterceptor;
    /**
     * 配置自定义拦截器，拦截所有请求
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	// TODO Auto-generated method stub
    	registry.addInterceptor(authLoginInterceptor).addPathPatterns("/**");
    }
   
    
    
    @Autowired
    private UEditorConfig uEditorConfig;

    @Bean
    @ConditionalOnMissingBean(ActionEnter.class)
    public ActionEnter actionEnter() {
        ActionEnter actionEnter = new ActionEnter(ConfigManager.getInstance(uEditorConfig));
        return actionEnter;
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


    /**
     * 用于处理编码问题
     *
     * @return
     */
    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
}

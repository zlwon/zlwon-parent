package com.zlwon.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.zlwon.web"})  //需要扫描的包路径
public class SpringfoxConfig {
    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.zlwon.web")).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("管理平台接口").description("").version("1.0").build();
    }
}

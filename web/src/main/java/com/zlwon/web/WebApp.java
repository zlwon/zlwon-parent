package com.zlwon.web;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.github.pagehelper.PageHelper;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class WebApp {
	
    public static void main(String[] args){
        SpringApplication.run(WebApp.class, args);
        System.out.println("App start ...");
    }
}

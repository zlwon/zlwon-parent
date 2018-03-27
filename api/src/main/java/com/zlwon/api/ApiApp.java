package com.zlwon.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ApiApp {
    public static void main(String[] args){
        SpringApplication.run(ApiApp.class, args);
        System.out.println("App start ...");
    }
}

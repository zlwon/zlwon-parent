package com.zlwon.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.zlwon"})
@EnableMongoRepositories("com.zlwon.nosql.dao")
public class WebApp {
	
    public static void main(String[] args){
        SpringApplication.run(WebApp.class, args);
        System.out.println("App start ...");
    }
}

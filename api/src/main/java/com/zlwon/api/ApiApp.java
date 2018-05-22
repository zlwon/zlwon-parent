package com.zlwon.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.zlwon"})
@EnableMongoRepositories("com.zlwon.nosql.dao")
@EnableTransactionManagement
public class ApiApp {
    public static void main(String[] args){
        SpringApplication.run(ApiApp.class, args);
        System.out.println("App start ...");
    }
}

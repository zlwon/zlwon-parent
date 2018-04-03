package com.zlwon.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "com.zlwon" })
@EnableMongoRepositories("com.zlwon.nosql.dao")
@EnableTransactionManagement
public class PcApp {

	public static void main(String[] args) {
		SpringApplication.run(PcApp.class, args);
		System.out.println("App start ...");
	}
}

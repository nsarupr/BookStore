package com.nsarupri.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class BookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

	@Bean
	public ThreadPoolTaskExecutor executor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadGroupName("MyThreadGroup");
		executor.setThreadNamePrefix("MyThreadNamePrefix");
		executor.setCorePoolSize(5000);
		return executor;
	}
}

package com.vantalii.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.vantalii.*")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

package com.youragent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class YouragentApplication {

	public static void main(String[] args) {
        
		ApplicationContext context = SpringApplication.run(YouragentApplication.class, args);
		System.out.println(context.toString());
	}

}
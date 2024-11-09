package com.youragent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class YouragentApplication {

	public static void main(String[] args) {
        
		ApplicationContext context = SpringApplication.run(YouragentApplication.class, args);
		System.out.println(context.toString());
	}

}
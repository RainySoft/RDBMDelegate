/**
 *  Copyright (c)  RainySoft.com 
 */
package com.rainsoft.rdbm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RdbmDelegateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RdbmDelegateApplication.class, args);
	}
	
	@Bean
	public DataSourceBuilder getBuilder() {
		return new DataSourceBuilder();
	}
}

package com.camp.boot.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAutoConfiguration {
	
	@Bean
	public PrefectBean getBean()
	{
		return new PrefectBean();
	}

}

package com.camp.boot.service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

//@Component
//@ConfigurationProperties(locations ="classpath:conf.properties")
@org.springframework.context.annotation.Configuration
@Import(com.camp.boot.service.configuration.Config.class)
public class Configuration {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

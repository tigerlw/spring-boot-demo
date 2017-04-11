package com.ucloudlink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.ucloudlink.boot.service.EnableMyApplication;
import com.ucloudlink.boot.service.configuration.PrefectBean;
import com.ucloudlink.boot.service.configuration.SecAutoConfiguration;

//@EnableAutoConfiguration
//@EnableMyApplication
@SpringBootApplication
public class BootStartApplication 
{
	
	public static void main(String args[])
	{
		ConfigurableApplicationContext context = SpringApplication.run(BootStartApplication.class, args);
		
		SecAutoConfiguration bean = context.getBean(SecAutoConfiguration.class);
		
		System.out.println(bean.queryBean());
		
		
	}


}

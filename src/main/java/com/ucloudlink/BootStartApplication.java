package com.ucloudlink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.ucloudlink.boot.service.EnableMyApplication;
import com.ucloudlink.boot.service.configuration.PrefectBean;

@EnableAutoConfiguration
@EnableMyApplication
public class BootStartApplication 
{
	
	public static void main(String args[])
	{
		ConfigurableApplicationContext context = SpringApplication.run(BootStartApplication.class, args);
		
		PrefectBean bean = context.getBean(PrefectBean.class);
		
		System.out.println(bean.getOutput());
		
		
	}


}

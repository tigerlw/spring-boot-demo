package com.camp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.camp.akka.main.RouterActorMain;
import com.camp.boot.service.EnableMyApplication;
import com.camp.boot.service.configuration.PrefectBean;
import com.camp.boot.service.configuration.SecAutoConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;


//@EnableAutoConfiguration
//@EnableMyApplication
@SpringBootApplication
public class BootStartApplication 
{
	
	public static void main(String args[])
	{
		/*ConfigurableApplicationContext context = SpringApplication.run(BootStartApplication.class, args);
		
		ComboPooledDataSource bean = (ComboPooledDataSource) context.getBean("dataSourceC");*/
		
		//RouterActorMain.main(args);
		
		/*SecAutoConfiguration bean = context.getBean(SecAutoConfiguration.class);
		
		System.out.println(bean.queryBean());*/
		
		SpringApplication.run(BootStartApplication.class, args);	
		
		
	}


}

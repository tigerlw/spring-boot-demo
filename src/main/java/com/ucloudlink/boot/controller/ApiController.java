package com.ucloudlink.boot.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import com.ucloudlink.boot.bean.JavassitUtils;
import com.ucloudlink.boot.bean.UserService;

@RestController
public class ApiController implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	@RequestMapping(value = "/addApi" ,method = RequestMethod.GET)
	public String addApi(@RequestParam String apiName) throws Exception
	{
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;  
		
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext  
		        .getBeanFactory();  
		
		
		Class ctl = JavassitUtils.createDynamicClazz();
		
		/*ctl.getDeclaredMethod("sayHello",String.class).getAnnotations();*/
		
		
		
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder  
		        .genericBeanDefinition(ctl); 
		
		defaultListableBeanFactory.registerBeanDefinition("hello",  
		        beanDefinitionBuilder.getBeanDefinition());  
		
		//configurableApplicationContext.refresh();
		
		/*UserService service = (UserService) applicationContext.getBean("userService");
		
		String result = service.queryUser(apiName);*/
		
		RequestMappingInfoHandlerMapping servlet = (RequestMappingInfoHandlerMapping) applicationContext.getBean(RequestMappingInfoHandlerMapping.class);
		
		
		servlet.afterPropertiesSet();
		
		return apiName;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

}

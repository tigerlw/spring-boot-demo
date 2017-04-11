package com.ucloudlink.boot.bean;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

@RestController
public class UserService {
	
	@RequestMapping(value = "/query")
	public String queryUser(@RequestParam String name) throws NotFoundException, ClassNotFoundException
	{
		
		/*ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get("com.ucloudlink.boot.controller.ApiController");
		
		cc.getAnnotations();
		
		cc.getDeclaredMethod("addApi").getAnnotations();*/
		
		return name;
	}

}

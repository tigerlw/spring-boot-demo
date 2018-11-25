package com.camp.boot.bean;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationImpl;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

public class JavassitUtils {
	
	static public Class<?> createDynamicClazz() throws Exception
	{
		
		ClassPool pool = ClassPool.getDefault();
		
		CtClass cc = pool.makeClass("com.camp.DynamicHelloWorld");
		
		CtMethod ctMethod = CtNewMethod.make("public String sayHello() { return \"hello\"; }", cc);
        cc.addMethod(ctMethod); 
        
        ClassFile ccFile = cc.getClassFile();
        ConstPool constPool = ccFile.getConstPool();
        
        // 添加类注解
        AnnotationsAttribute bodyAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation bodyAnnot = new Annotation("org.springframework.web.bind.annotation.RestController", constPool);
        bodyAttr.addAnnotation(bodyAnnot);
         
        ccFile.addAttribute(bodyAttr);
        

		
		
		
        
        // 添加方法注解
        AnnotationsAttribute methodAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation methodAnnot = new Annotation("org.springframework.web.bind.annotation.RequestMapping", constPool);
        
        ArrayMemberValue arrayMemberValue = new ArrayMemberValue(constPool);
        
        StringMemberValue [] strArray = new StringMemberValue [1];
        strArray[0]= new StringMemberValue("/sayHelloWorld",constPool);
        
        arrayMemberValue.setValue(strArray);
        
        methodAnnot.addMemberValue("value", arrayMemberValue);
        
        EnumMemberValue mb = new EnumMemberValue(constPool);
        mb.setType("org.springframework.web.bind.annotation.RequestMethod");
        mb.setValue("GET");
        
        ArrayMemberValue arrayMemberValue2 = new ArrayMemberValue(constPool);
        EnumMemberValue [] enumArray = new EnumMemberValue [1];
        enumArray[0] = mb;
        
        arrayMemberValue2.setValue(enumArray);
        
        
        methodAnnot.addMemberValue("method", arrayMemberValue2);
        methodAttr.addAnnotation(methodAnnot);
         
         
        ctMethod.getMethodInfo().addAttribute(methodAttr);
        
        cc.getDeclaredMethod("sayHello").getAnnotations();
		
		return cc.toClass();
		
	}

}

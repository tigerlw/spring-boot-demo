package com.camp.boot.service.configuration;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class Config implements ImportSelector{
	
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String[] selectImports(AnnotationMetadata arg0) {
		// TODO Auto-generated method stub
		return new String[]{PrefectBean.class.getName()};
	}
	
	

}

package com.ucloudlink.boot.service.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecAutoConfiguration {
	
	private List<PrefectBean> beans;
	
	public SecAutoConfiguration(List<PrefectBean> beans)
	{
		this.beans=beans;
	}
	
	public String queryBean()
	{
		return beans.get(0).getOutput();
	}
	

}

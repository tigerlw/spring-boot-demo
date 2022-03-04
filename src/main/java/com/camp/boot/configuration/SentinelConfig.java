package com.camp.boot.configuration;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;

@Configuration
public class SentinelConfig {
	
	@Bean
	public RequestOriginParser builderParser()
	{
		//Optional<RequestOriginParser>
		
		RequestOriginParser parser = new RequestOriginParser() {
            @Override
            public String parseOrigin(HttpServletRequest request) {
                return request.getHeader("S-user");
            }
        };
        
        
        //Optional<RequestOriginParser> result = Optional.ofNullable(parser);
        
        return parser;
	}

}

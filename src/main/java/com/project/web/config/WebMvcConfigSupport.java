package com.project.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

//@Configuration
public class WebMvcConfigSupport extends WebMvcConfigurationSupport {
	@Override
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter requestMappingHandlerAdapter = super.requestMappingHandlerAdapter();
		requestMappingHandlerAdapter.setSynchronizeOnSession(true);
		requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
		return requestMappingHandlerAdapter;
	}
}
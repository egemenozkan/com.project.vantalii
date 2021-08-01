package com.project.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.web.interceptor.ExtendedCookieLocaleResolver;
import com.project.web.interceptor.ExtendedLocaleChangeInterceptor;
import com.project.web.interceptor.ExtendedResourceBundleMessageSource;
import com.project.web.interceptor.SearchFormInterceptor;
import com.project.web.interceptor.SessionTimerInterceptor;
import com.project.web.interceptor.SiteInterceptor;
import com.project.web.interceptor.UserInterceptor;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		 registry.addInterceptor(localisationInteceptor());
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(searchFormInterceptor());
		registry.addInterceptor(siteInterceptor());
		registry.addInterceptor(new UserInterceptor());
		registry.addInterceptor(new SessionTimerInterceptor());
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

		// if (!registry.hasMappingForPattern("/**")) {
		// registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
		// }
		// .addResourceHandler("/files/**")
		// .addResourceLocations("file:/opt/files/");
	}
	
	@Bean
	public SiteInterceptor siteInterceptor() {
		return new SiteInterceptor();
	}

	@Bean
	public SearchFormInterceptor searchFormInterceptor() {
		return new SearchFormInterceptor();
	}
	
	@Bean
	public ExtendedCookieLocaleResolver localeResolver() {
		ExtendedCookieLocaleResolver localeResolver = new ExtendedCookieLocaleResolver();
		localeResolver.setCookieName("lang");
		localeResolver.setCookieMaxAge(3600);
		return localeResolver;

	}

	@Bean
	public ExtendedLocaleChangeInterceptor localeChangeInterceptor() {
		ExtendedLocaleChangeInterceptor interceptor = new ExtendedLocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}
	
	@Bean
	public ExtendedResourceBundleMessageSource messageSource() {
		ExtendedResourceBundleMessageSource messageSource = new ExtendedResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	

}

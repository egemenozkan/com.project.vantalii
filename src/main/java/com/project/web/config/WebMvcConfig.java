package com.project.web.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.google.gson.Gson;
import com.project.web.interceptor.ExtendedCookieLocaleResolver;
import com.project.web.interceptor.ExtendedLocaleChangeInterceptor;
import com.project.web.interceptor.ExtendedResourceBundleMessageSource;
import com.project.web.interceptor.SearchFormInterceptor;
import com.project.web.interceptor.SessionTimerInterceptor;
import com.project.web.interceptor.SiteInterceptor;
import com.project.web.interceptor.UserInterceptor;

import freemarker.cache.NullCacheStorage;
import freemarker.template.TemplateException;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.project.web")
@PropertySource({ "classpath:config/application.properties", "classpath:config/application-${spring.profiles.active}.properties" })
// @EnableScheduling
public class WebMvcConfig implements WebMvcConfigurer {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/" };

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(localisationInteceptor());
		registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(searchFormInterceptor());
		registry.addInterceptor(siteInterceptor());
        registry.addInterceptor(new UserInterceptor());
        registry.addInterceptor(new SessionTimerInterceptor());
	}


	@Bean
	public FreeMarkerViewResolver freemarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setCache(false);
		resolver.setPrefix("");
		resolver.setSuffix(".ftl");
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setExposeRequestAttributes(true);
		resolver.setExposeSessionAttributes(true);
		resolver.setExposeSpringMacroHelpers(true);
		return resolver;
	}

//	@Bean
//	public FreeMarkerConfigurer freemarkerConfig() {
//		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
//		freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates");
//		
//		return freeMarkerConfigurer;
//	}

	
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() throws IOException, TemplateException
	{
	    FreeMarkerConfigurer configurer = new FreeMarkerConfigurer()
	    {

	        @Override
	        protected void postProcessConfiguration(freemarker.template.Configuration config) throws IOException, TemplateException
	        { 
	            config.setCacheStorage(new NullCacheStorage()); 
	        	config.setTemplateUpdateDelayMilliseconds(0);
	        }
	    };
	    configurer.setDefaultEncoding("UTF-8"); 
	    configurer.setPreferFileSystemAccess(false);
	    configurer.setTemplateLoaderPath("classpath:templates");

	    return configurer;
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
	public ExtendedResourceBundleMessageSource messageSource() {
		ExtendedResourceBundleMessageSource messageSource = new ExtendedResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	// @Bean
	// public CookieLocaleResolver localeResolver() {
	// CookieLocaleResolver localeResolver = new CookieLocaleResolver();
	// localeResolver.setDefaultLocale(new Locale("tr"));
	// localeResolver.setCookieName("lang");
	// localeResolver.setCookieMaxAge(3600);
	// return localeResolver;
	// }

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
	public SiteInterceptor siteInterceptor() {
		return new SiteInterceptor();
	}
	
	@Bean
	public SearchFormInterceptor searchFormInterceptor() {
		return new SearchFormInterceptor();
	}

	@Bean
	public Gson gson() {
		return new Gson();
	}
	

	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
	    return new ByteArrayHttpMessageConverter();
	}

}

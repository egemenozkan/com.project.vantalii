package com.vantalii.web.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.google.gson.Gson;
import com.vantalii.web.interceptor.ExtendedCookieLocaleResolver;
import com.vantalii.web.interceptor.ExtendedLocaleChangeInterceptor;
import com.vantalii.web.interceptor.ExtendedResourceBundleMessageSource;
import com.vantalii.web.interceptor.SearchFormInterceptor;
import com.vantalii.web.interceptor.SessionTimerInterceptor;
import com.vantalii.web.interceptor.SiteInterceptor;
import com.vantalii.web.interceptor.UserInterceptor;

import freemarker.cache.NullCacheStorage;
import freemarker.template.TemplateException;

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

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
		propsConfig.setLocation(new ClassPathResource("git.properties"));
		propsConfig.setIgnoreResourceNotFound(true);
		propsConfig.setIgnoreUnresolvablePlaceholders(true);
		return propsConfig;
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

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer() {

			@Override
			protected void postProcessConfiguration(freemarker.template.Configuration config)
					throws IOException, TemplateException {
				config.setCacheStorage(new NullCacheStorage());
				config.setTemplateUpdateDelayMilliseconds(0);
			}
		};
		configurer.setDefaultEncoding("UTF-8");
		configurer.setPreferFileSystemAccess(false);
		configurer.setTemplateLoaderPath("classpath:templates");

		return configurer;
	}

	@Bean
	public Gson gson() {
		return new Gson();
	}

	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
		return new ByteArrayHttpMessageConverter();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

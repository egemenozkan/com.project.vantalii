package com.project.web.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.google.gson.Gson;
import com.project.web.interceptor.ExtendedCookieLocaleResolver;
import com.project.web.interceptor.ExtendedLocaleChangeInterceptor;
import com.project.web.interceptor.SessionTimerInterceptor;
import com.project.web.interceptor.SiteInterceptor;
import com.project.web.interceptor.UserInterceptor;

import freemarker.template.TemplateModel;
import kr.pe.kwonnam.freemarker.inheritance.BlockDirective;
import kr.pe.kwonnam.freemarker.inheritance.ExtendsDirective;
import kr.pe.kwonnam.freemarker.inheritance.PutDirective;

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
		registry.addInterceptor(siteInteceptor());
        registry.addInterceptor(new UserInterceptor());
        registry.addInterceptor(new SessionTimerInterceptor());
	}

	@Bean
	public Map<String, TemplateModel> freemarkerLayoutDirectives() {
		Map<String, TemplateModel> freemarkerLayoutDirectives = new HashMap<>();
		freemarkerLayoutDirectives.put("extends", new ExtendsDirective());
		freemarkerLayoutDirectives.put("block", new BlockDirective());
		freemarkerLayoutDirectives.put("put", new PutDirective());
		return freemarkerLayoutDirectives;
	}

	@Bean
	public FreeMarkerViewResolver freemarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setCache(true);
		resolver.setPrefix("");
		resolver.setSuffix(".ftl");
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setExposeRequestAttributes(true);
		resolver.setExposeSessionAttributes(true);
		resolver.setExposeSpringMacroHelpers(true);

		return resolver;
	}

	@Bean
	public FreeMarkerConfigurer freemarkerConfig() {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates");
		Map<String, Object> freemarkerVariables = new HashMap<>();
		freemarkerVariables.put("layout", freemarkerLayoutDirectives());

		freeMarkerConfigurer.setFreemarkerVariables(freemarkerVariables);
		return freeMarkerConfigurer;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

		// if (!registry.hasMappingForPattern("/**")) {
		// registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
		// }
		// .addResourceHandler("/files/**")
		// .addResourceLocations("file:/opt/files/");
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
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
	public SiteInterceptor siteInteceptor() {
		return new SiteInterceptor();
	}

	@Bean
	public Gson gson() {
		return new Gson();
	}

}

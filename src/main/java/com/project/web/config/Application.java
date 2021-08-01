package com.project.web.config;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.google.gson.Gson;

import freemarker.cache.NullCacheStorage;
import freemarker.template.TemplateException;

@Configuration
@ComponentScan(basePackages = "com.project.*")
// @EnableScheduling
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	  @Bean
	    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
	        PropertySourcesPlaceholderConfigurer propsConfig 
	          = new PropertySourcesPlaceholderConfigurer();
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

//	@Bean
//	public FreeMarkerConfigurer freemarkerConfig() {
//		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
//		freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates");
//		
//		return freeMarkerConfigurer;
//	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() throws IOException, TemplateException {
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




	// @Bean
	// public CookieLocaleResolver localeResolver() {
	// CookieLocaleResolver localeResolver = new CookieLocaleResolver();
	// localeResolver.setDefaultLocale(new Locale("tr"));
	// localeResolver.setCookieName("lang");
	// localeResolver.setCookieMaxAge(3600);
	// return localeResolver;
	// }



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
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;

	}

}

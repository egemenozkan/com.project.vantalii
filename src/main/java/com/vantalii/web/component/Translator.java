package com.vantalii.web.component;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.vantalii.web.interceptor.ExtendedResourceBundleMessageSource;

@Component
public class Translator {

	private static ExtendedResourceBundleMessageSource messageSource;

	@Autowired
	Translator(ExtendedResourceBundleMessageSource messageSource) {
		Translator.messageSource = messageSource;
	}

	public static String toLocale(String msgCode) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(msgCode, null, locale);
	}

	public static Map<String, String> getAllMessages() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getAllMessages(locale);
	}
}

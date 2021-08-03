package com.vantalii.web.interceptor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class ExtendedResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {
	public Map<String, String> getAllMessages(Locale locale) {
		Map<String, String> messages = new HashMap<>();

		PropertiesHolder propertiesHolder = getMergedProperties(locale);
		Properties properties = propertiesHolder.getProperties();

		for (Object key : properties.keySet()) {
			messages.put((String) key, (String) properties.get(key));
		}

		return messages;
	}
}

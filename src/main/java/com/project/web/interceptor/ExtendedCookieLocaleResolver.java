package com.project.web.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.project.common.enums.Language;
import com.project.common.utils.WebUtils;

public class ExtendedCookieLocaleResolver extends CookieLocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String languageCode = WebUtils.getURILanguageCode(request.getRequestURI());
		
		if (languageCode == null  || !("tr".equalsIgnoreCase(languageCode) || "en".equalsIgnoreCase(languageCode))) {
			languageCode = Language.RUSSIAN.getCode().toLowerCase();
		}
		
		return new Locale(languageCode);
	}

}

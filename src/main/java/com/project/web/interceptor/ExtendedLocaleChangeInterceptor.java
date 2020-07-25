package com.project.web.interceptor;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.project.common.enums.Language;
import com.project.common.utils.WebUtils;

public class ExtendedLocaleChangeInterceptor extends LocaleChangeInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		String languageCode = WebUtils.getURILanguageCode(request.getRequestURI());

		if (languageCode == null || !("tr".equalsIgnoreCase(languageCode) || "en".equalsIgnoreCase(languageCode))) {
			languageCode = Language.RUSSIAN.getCode().toLowerCase();
		}

		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		localeResolver.setLocale(request, response, new Locale(languageCode));

		return true;
	}

}

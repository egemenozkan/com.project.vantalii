package com.project.web.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.project.api.data.enums.Language;
import com.project.web.model.WebPageModel;
import com.project.web.utils.WebUtils;

public class SiteInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(SiteInterceptor.class);
	@Autowired
	Gson gson;

	@Value("${site.scheme}")
	private String scheme;

	@Value("${site.environment}")
	private String environment;

	/**
	 * Executed before actual handler is executed
	 **/
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		// logger.info("[preHandle][" + request + "]" + "[" + request.getMethod() + "]"
		// + request.getRequestURI() + getParameters(request));

		return true;
	}

	/**
	 * Executed before after handler is executed
	 **/
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {
		if (modelAndView == null) {
			return;
		}
		String languageCode = WebUtils.getURILanguageCode(request.getRequestURI());

		if (languageCode == null || !("tr".equalsIgnoreCase(languageCode) || "en".equalsIgnoreCase(languageCode))) {
			languageCode = Language.RUSSIAN.getCode().toLowerCase();
		}

		WebPageModel webPage = new WebPageModel();
		webPage.setHost(request.getHeader("host"));
		webPage.setScheme(scheme);
		webPage.setLanguage(Language.getByCode(languageCode));

		if ("dev".equals(environment)) {
			webPage.setCanonical(request.getRequestURL().toString());
		} else if ("prod".equals(environment)) {
			webPage.setCanonical(request.getRequestURL().toString().replace("http:", "https:"));
		}

		StringBuilder baseUrlStrBuilder = new StringBuilder();
		baseUrlStrBuilder.append(webPage.getScheme());
		baseUrlStrBuilder.append("://");
		baseUrlStrBuilder.append(webPage.getHost());

		if (!webPage.getHost().contains("vantalii.ru")) {
			baseUrlStrBuilder.append("/");
			baseUrlStrBuilder.append(webPage.getLanguage().getCode().toLowerCase());
		}
		webPage.setBaseUrl(baseUrlStrBuilder.toString());

		if (logger.isInfoEnabled()) {
			logger.info("webPage: {}", gson.toJson(webPage));
		}

		modelAndView.getModelMap().addAttribute("webPage", webPage);
	}

	/**
	 * Executed after complete request is finished
	 **/
	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final Exception ex) throws Exception {
		if (ex != null)
			ex.printStackTrace();
		logger.info("[afterCompletion][" + request + "][exception: " + ex + "]");
	}

	private String getParameters(final HttpServletRequest request) {
		final StringBuffer posted = new StringBuffer();
		final Enumeration<?> e = request.getParameterNames();
		if (e != null)
			posted.append("?");
		while (e != null && e.hasMoreElements()) {
			if (posted.length() > 1)
				posted.append("&");
			final String curr = (String) e.nextElement();
			posted.append(curr).append("=");
			if (curr.contains("password") || curr.contains("answer") || curr.contains("pwd")) {
				posted.append("*****");
			} else {
				posted.append(request.getParameter(curr));
			}
		}

		final String ip = request.getHeader("X-FORWARDED-FOR");
		final String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
		if (ipAddr != null && !ipAddr.isEmpty())
			posted.append("&_psip=" + ipAddr);
		return posted.toString();
	}

	private String getRemoteAddr(final HttpServletRequest request) {
		final String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
		if (ipFromHeader != null && ipFromHeader.length() > 0) {
			logger.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
			return ipFromHeader;
		}
		return request.getRemoteAddr();
	}
}

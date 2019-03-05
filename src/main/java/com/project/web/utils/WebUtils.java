package com.project.web.utils;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class WebUtils {

	private WebUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static long getIdOfSlug(String slug) {
		if (slug != null && !slug.isBlank()) {
			try {
				String[] arrStr = slug.split("-");
				String idStr = arrStr[arrStr.length - 1];
				return Long.valueOf(idStr);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}
	
	public static String[] parseURI(String uri) {
		if (uri != null && !uri.isBlank()) {
			return uri.split("/");
		}
		return new String[0];
	}
	
	public static String getURILanguageCode(String uri) {
		String[] uriStrings = parseURI(uri);
		if (uriStrings.length > 1) {
			return uriStrings[1];
		}
		return null;
	}
	
	
	/** Cookie **/
	public static String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) {
		return getCookieValue(request, cookieName) == null ? defaultValue : getCookieValue(request, cookieName);
	}
	
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		if (request.getCookies() == null) {
			return null;
		}
	    return Arrays.stream(request.getCookies())
	            .filter(c -> c.getName().equals(cookieName))
	            .findFirst()
	            .map(Cookie::getValue)
	            .orElse(null);
	}
	
	/** End of Cookie **/
}

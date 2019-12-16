package com.project.web.interceptor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.project.api.data.enums.Language;
import com.project.api.data.enums.MainType;
import com.project.api.data.enums.PlaceType;
import com.project.web.component.Translator;
import com.project.web.model.AutocompleteItem;
import com.project.web.model.WebPageModel;
import com.project.web.utils.WebUtils;

public class SearchFormInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(SearchFormInterceptor.class);
	
	@Autowired
	Gson gson;
	
	/**
	 * Executed before actual handler is executed
	 **/
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		return true;
	}

	/**
	 * Executed before after handler is executed
	 **/
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {
		if (modelAndView == null) {
//			logger.info("::postHandle modelAndVİew: {}", gson.toJson(modelAndView));
			return;
		}
		
	
		List<AutocompleteItem>  popularPlaces = new ArrayList<>();
		popularPlaces.add(new AutocompleteItem("Alışveriş2", "Alışveriş", "SHOPPING", MainType.SHOPPING.getSlug()));
		
		modelAndView.getModel().put("popularPlaces", gson.toJson(popularPlaces));

	}

	/**
	 * Executed after complete request is finished
	 **/
	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final Exception ex) throws Exception {

		
		
		
	}

}

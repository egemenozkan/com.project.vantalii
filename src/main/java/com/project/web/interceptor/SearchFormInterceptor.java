package com.project.web.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.project.api.data.enums.MainType;
import com.project.api.data.model.event.EventType;
import com.project.web.model.AutocompleteItem;

public class SearchFormInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(SearchFormInterceptor.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	Gson gson;

	private static final List<AutocompleteItem> popularPlaces = new ArrayList<>();
	private static final List<AutocompleteItem> popularEvents = new ArrayList<>();

	/**
	 * Executed before actual handler is executed
	 **/
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
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

//		modelAndView.getModel().put("popularPlaces", gson.toJson(popularPlaces));
//
//		modelAndView.getModel().put("popularEvents", gson.toJson(popularEvents));

	}

	/**
	 * Executed after complete request is finished
	 **/
	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, final Exception ex) throws Exception {
		for (int i = 0; i < MainType.values().length; i++) {
			if (MainType.values()[i].getSlug().length() == 0) {
				continue;
			}
			popularPlaces.add(new AutocompleteItem(
					messageSource.getMessage("places.mainType." + MainType.values()[i].name(), null, new Locale("tr")),
					messageSource.getMessage("places.mainType." + MainType.values()[i].name(), null, new Locale("tr")),
					MainType.values()[i].toString(), "/ru/places/m/" + MainType.values()[i].getSlug()));
		}

		for (int j = 0; j < EventType.values().length; j++) {
			if (EventType.values()[j].getSlug().length() == 0) {
				continue;
			}
			popularEvents.add(new AutocompleteItem(
					messageSource.getMessage("events.type." + EventType.values()[j].name(), null, new Locale("tr")),
					messageSource.getMessage("events.type." + EventType.values()[j].name(), null, new Locale("tr")),
					EventType.values()[j].toString(), "/ru/events/m/" + EventType.values()[j].getSlug()));
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

}

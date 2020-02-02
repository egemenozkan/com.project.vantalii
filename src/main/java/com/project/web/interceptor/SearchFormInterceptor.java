package com.project.web.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

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
import com.project.web.model.WebPageModel;

public class SearchFormInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(SearchFormInterceptor.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	Gson gson;

	private static final TreeMap<String, List<AutocompleteItem>> popularPlacesMap = new TreeMap<String, List<AutocompleteItem>>();
	private static final TreeMap<String, List<AutocompleteItem>> popularEventsMap = new TreeMap<String, List<AutocompleteItem>>();

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

//		if (modelAndView.getModelMap().addAttribute("webPage", webPage);
		WebPageModel webPage = (WebPageModel) modelAndView.getModel().get("webPage");
		if (webPage != null) {
		  String languageCode =  webPage.getLanguage().getCode().toLowerCase();
		  modelAndView.getModel().put("popularPlaces", gson.toJson(popularPlacesMap.get(languageCode)));
		  modelAndView.getModel().put("popularEvents", gson.toJson(popularEventsMap.get(languageCode)));
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

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		for (String languageCode : Arrays.asList("tr", "en", "ru")) {
			List<AutocompleteItem> popularPlaces =  popularPlacesMap.getOrDefault(languageCode, new ArrayList<>());
			List<AutocompleteItem> popularEvents =  popularEventsMap.getOrDefault(languageCode, new ArrayList<>());

			for (int i = 0; i < MainType.values().length; i++) {
				if (MainType.values()[i].getSlug().length() == 0 || !MainType.values()[i].isShowInPopulars()) {
					continue;
				}
				popularPlaces.add(new AutocompleteItem(
						messageSource.getMessage("places.mainType." + MainType.values()[i].name(), null,
								new Locale(languageCode)),
						messageSource.getMessage("places.mainType." + MainType.values()[i].name(), null,
								new Locale(languageCode)),
						MainType.values()[i].toString(), "/places/m/" +  ("ru".equals(languageCode) ? "" : languageCode + "/")  + MainType.values()[i].getSlug()));
			}
			popularPlacesMap.put(languageCode, popularPlaces);
			for (int j = 0; j < EventType.values().length; j++) {
				if (EventType.values()[j].getSlug().length() == 0) {
					continue;
				}
				popularEvents.add(new AutocompleteItem(
						messageSource.getMessage("events.type." + EventType.values()[j].name(), null,
								new Locale(languageCode)),
						messageSource.getMessage("events.type." + EventType.values()[j].name(), null,
								new Locale(languageCode)),
						EventType.values()[j].toString(), "/events/m/" + ("ru".equals(languageCode) ? "" : languageCode + "/") + EventType.values()[j].getSlug()));
			}
			popularEventsMap.put(languageCode, popularEvents);

			
		}
	}

}

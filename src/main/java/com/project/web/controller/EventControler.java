package com.project.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.project.api.data.enums.Language;
import com.project.api.data.model.event.EventLandingPage;
import com.project.web.service.IEventService;
import com.project.web.utils.WebUtils;

@Controller
public class EventControler {

	@Autowired
	private IEventService eventService;

	final Logger LOG = LoggerFactory.getLogger(EventControler.class);

	@GetMapping({"/events/{slug}", "/{language}/events/{slug}"})
	public ModelAndView getPlace(Model model, HttpServletRequest request, @PathVariable(required=false, name="language") String language, @PathVariable String slug) throws NoHandlerFoundException {
		long id = WebUtils.getIdOfSlug(slug);
		EventLandingPage page = null;

		if (language == null) {
			language = WebUtils.getCookieValue(request, "lang",  Language.RUSSIAN.getCode().toLowerCase());
		}
		StringBuilder url = new StringBuilder(language);
		url.append("/events/");
		url.append(slug);

		if (id > 0) {
			page = eventService.getEventLandingPage(id, language);
		}
		/** REDIRECT - 404 **/
		if (page == null) {
			LOG.warn("::redirect status 404  {}-> {}", url, "404");
			throw new NoHandlerFoundException(HttpMethod.GET.toString(), url.toString(), new HttpHeaders());
		}

//		9
		if (page.getSlug() != null && !slug.equalsIgnoreCase(page.getSlug())) {
			StringBuilder redirectUrl = new StringBuilder("redirect:/");
			if (!(request.getHeader("host").contains("vantalii.ru") && "ru".equalsIgnoreCase(language))) {
				redirectUrl.append(page.getLanguage().getCode().toLowerCase());
			} 
			redirectUrl.append("/events/");
			redirectUrl.append(page.getSlug());
			
			LOG.warn("::redirect status 301  {}-> {}", url, redirectUrl);

			return new ModelAndView(redirectUrl.toString(), HttpStatus.PERMANENT_REDIRECT);
		}
		
		model.addAttribute("page", page);
		
		return new ModelAndView("events/page");
	}
}

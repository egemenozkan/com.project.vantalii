package com.project.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.google.gson.Gson;
import com.project.api.data.enums.EventPeriodType;
import com.project.api.data.enums.EventType;
import com.project.api.data.enums.Language;
import com.project.api.data.model.common.Address;
import com.project.api.data.model.common.Contact;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;
import com.project.api.data.model.place.Place;
import com.project.web.service.IEventService;
import com.project.web.utils.WebUtils;

@Controller
public class EventControler {

	@Autowired
	private IEventService eventService;

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	Gson gson;

	@GetMapping({ "/events", "/{language}/events" })
	public ModelAndView eventsHomepage(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language) {

		EventRequest eventRequest = new EventRequest();
		eventRequest.setType(EventType.CONCERT);
		List<EventLandingPage> events = eventService.getEventLandingPages(eventRequest);

		model.addAttribute("events", events);
		logger.info("events: {}", gson.toJson(events));
		return new ModelAndView("events/index");
	}

	@GetMapping({ "/events/json" })
	@ResponseBody
	public List<Event> events(Model model, HttpServletRequest request) {
		EventRequest eventRequest = new EventRequest();
		eventRequest.setType(EventType.CONCERT);
		eventRequest.setLanguage(Language.TURKISH);
		List<Event> events = eventService.getEvents(eventRequest);
		model.addAttribute("events", events);
		logger.info("events: {}", gson.toJson(events));
		return events;
	}

	@GetMapping({ "/eventsJson" })
	@ResponseBody
	public List<Event> eventsJson(Model model, HttpServletRequest request) {
		EventRequest eventRequest = new EventRequest();
		eventRequest.setType(EventType.CONCERT);
		eventRequest.setLanguage(Language.TURKISH);
		List<Event> events = eventService.getEvents(eventRequest);
		model.addAttribute("events", events);
		logger.info("events: {}", gson.toJson(events));
		return events;
	}

	@GetMapping({ "/events/{slug}", "/{language}/events/{slug}" })
	public ModelAndView getPlace(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language, @PathVariable String slug)
			throws NoHandlerFoundException {
		long id = WebUtils.getIdOfSlug(slug);
		EventLandingPage page = null;
//
//		if (language == null) {
//			language = WebUtils.getCookieValue(request, "lang",  Language.RUSSIAN.getCode().toLowerCase());
//		}
//		StringBuilder url = new StringBuilder(language);
//		url.append("/events/");
//		url.append(slug);
//
//		if (id > 0) {
//			page = eventService.getEventLandingPage(id, language);
//		}
//		/** REDIRECT - 404 **/
//		if (page == null) {
//			logger.warn("::redirect status 404  {}-> {}", url, "404");
//			throw new NoHandlerFoundException(HttpMethod.GET.toString(), url.toString(), new HttpHeaders());
//		}
//
////		9
//		if (page.getSlug() != null && !slug.equalsIgnoreCase(page.getSlug())) {
//			StringBuilder redirectUrl = new StringBuilder("redirect:/");
//			if (!(request.getHeader("host").contains("vantalii.ru") && "ru".equalsIgnoreCase(language))) {
//				redirectUrl.append(page.getLanguage().getCode().toLowerCase());
//			} 
//			redirectUrl.append("/events/");
//			redirectUrl.append(page.getSlug());
//			
//			logger.warn("::redirect status 301  {}-> {}", url, redirectUrl);
//
//			return new ModelAndView(redirectUrl.toString(), HttpStatus.PERMANENT_REDIRECT);
//		}
//		

		page = new EventLandingPage();
		page.setTitle("23 Nisan Ulusal Egemenlik ve Çocuk Bayramı");
		/* Event */
		Event event = new Event();
		event.setType(EventType.CONCERT);
		event.setPeriodType(EventPeriodType.SATURDAYS);
		/* Place */
		Place place = new Place();
		place.setName("Atatürk Kültür Merkezi");
		/* Place Address */
		Address address = new Address();
		address.setCity("Antalya");
		address.setRegion("Antalya Merkez");
		address.setSubregion("Lara");
		place.setAddress(address);

		Contact contact = new Contact();
		contact.setCallCenter("(555) 555 55 55");
		contact.setEmail("info@blabla.com");
		contact.setWeb("https://www.vantalii.com");
		contact.setWhatsapp("(555) 777 77 77");

		place.setContact(contact);
		event.setPlace(place);
		page.setEvent(event);

		page.setKeywords("Konser, 23 Nisan, Etkinlik");

		model.addAttribute("page", page);

		return new ModelAndView("events/page");
	}
}

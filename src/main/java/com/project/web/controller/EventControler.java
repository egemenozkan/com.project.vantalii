package com.project.web.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.google.gson.Gson;
import com.project.api.data.enums.LandingPageType;
import com.project.api.data.enums.Language;
import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.CommentResponse;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;
import com.project.api.data.model.event.EventType;
import com.project.api.data.model.file.MyFile;
import com.project.web.service.IEventService;
import com.project.web.service.IFileService;
import com.project.web.utils.WebUtils;

@Controller
public class EventControler {

	@Autowired
	private IEventService eventService;
	@Autowired
	private IFileService fileService;
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	Gson gson;

	@GetMapping({ "/events", "/{language}/events" })
	public ModelAndView eventsHomepage(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language) {

		EventRequest eventRequest = new EventRequest();
		eventRequest.setRandom(true);
		List<EventLandingPage> events = eventService.getEventLandingPages(eventRequest);

		model.addAttribute("events", events);
		if (logger.isInfoEnabled()) {
			logger.info("events: {}", gson.toJson(events));
		}
		return new ModelAndView("events/index");
	}

	@GetMapping({ "/events/json" })
	@ResponseBody
	public List<Event> events(Model model, HttpServletRequest request) {
		EventRequest eventRequest = new EventRequest();
		eventRequest.setType(EventType.CONCERT);
		eventRequest.setLanguage(Language.TURKISH);
		
		List<Event> events = eventService.getEvents(eventRequest);
		
		if (logger.isDebugEnabled()) {
			logger.debug("::getEvents response: {}, request: {}", gson.toJson(events), gson.toJson(eventRequest));
		}
		
		model.addAttribute("events", events);
		
		return events;
	}

	@GetMapping({ "/events/json/map" })
	@ResponseBody
	public Map<String, List<Event>> eventsMap(@RequestParam(defaultValue = "RU") String language,
			@RequestParam(required = false, defaultValue = "1") int type,
			@RequestParam(required = false, defaultValue = "") String types,
			@RequestParam(required = false, defaultValue = "0") int limit,
			@RequestParam(required = false, defaultValue = "false") boolean random,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
		
		EventRequest eventRequest = new EventRequest();
		
		if (type > 1) {
			eventRequest.setType(EventType.getById(type));
		}
		if (types != null && !types.isBlank() 
				&& !Arrays.asList(types.split(",")).contains(String.valueOf(EventType.ALL.getId()))) {
			eventRequest.setTypes(types.split(","));
		}
		if (limit > 0) {
			eventRequest.setLimit(limit);
		}
		if (random) {
			eventRequest.setRandom(Boolean.TRUE);
		}
		if (startDate != null) {
			eventRequest.setStartDate(startDate);
		}
		if (endDate != null) {
			eventRequest.setEndDate(endDate);
		}
		if (startDate != null &&  endDate == null) {
			eventRequest.setEndDate(startDate);
		}
		
		eventRequest.setLanguage(Language.getByCode(language));

 		Map<String, List<Event>> eventsMap = eventService.getEventsMap(eventRequest);

		if (logger.isDebugEnabled()) {
			logger.debug("::getEventsMap: {}", gson.toJson(eventsMap));
		}

		return eventsMap;
	}

	@GetMapping({ "/events/{slug}", "/{language}/events/{slug}" })
	public ModelAndView getEventPage(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language,
			@PathVariable String slug,
			@RequestParam(required = false, name = "tmid", defaultValue = "0") long timeTableId)
			throws NoHandlerFoundException {

		EventLandingPage page = null;

		long id = WebUtils.getIdOfSlug(slug);

		if (language == null) {
			language = WebUtils.getCookieValue(request, "lang", Language.RUSSIAN.getCode().toLowerCase());
		}
		StringBuilder url = new StringBuilder(language);
		url.append("/events/");
		url.append(slug);

		if (id > 0) {
			page = eventService.getEventLandingPage(id, language, timeTableId);
		}
		/** REDIRECT - 404 **/
		if (page == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("::redirect status 404  {}-> {}", url, "404");
			}
			throw new NoHandlerFoundException(HttpMethod.GET.toString(), url.toString(), new HttpHeaders());
		}

//		9
		if (page.getSlug() != null && !slug.equalsIgnoreCase(page.getSlug())) {
			StringBuilder redirectUrl = new StringBuilder("redirect:/");
			if (!(request.getHeader("host").contains("vantalii.ru") && "ru".equalsIgnoreCase(language))) {
				redirectUrl.append(page.getLanguage().getCode().toLowerCase());
			}
			redirectUrl.append("/events/").append(page.getSlug());

			if (logger.isWarnEnabled()) {
				logger.warn("::redirect status 301  {}-> {}", url, redirectUrl);
			}
			return new ModelAndView(redirectUrl.toString(), HttpStatus.PERMANENT_REDIRECT);
		}
//		

//		page = new EventLandingPage();
//		page.setTitle("23 Nisan Ulusal Egemenlik ve Çocuk Bayramı");
//		/* Event */
//		Event event = new Event();
//		event.setType(EventType.CONCERT);
//		event.setPeriodType(PeriodType.SATURDAYS);
//		/* Place */
//		Place place = new Place();
//		place.setName("Atatürk Kültür Merkezi");
//		/* Place Address */
//		Address address = new Address();
//		address.setCity("Antalya");
//		address.setRegion("Antalya Merkez");
//		address.setSubregion("Lara");
//		place.setAddress(address);
//
//		Contact contact = new Contact();
//		contact.setCallCenter("(555) 555 55 55");
//		contact.setEmail("info@blabla.com");
//		contact.setWeb("https://www.vantalii.com");
//		contact.setWhatsapp("(555) 777 77 77");
//
//		place.setContact(contact);
//		event.setPlace(place);
//		page.setEvent(event);
//
//		page.setKeywords("Konser, 23 Nisan, Etkinlik");

		model.addAttribute("page", page);

		return new ModelAndView("events/page");
	}
	
	@GetMapping({ "/events/comments" })
	public @ResponseBody CommentResponse getPlaceComments(Model model, @RequestParam(required = true) long id) {
		return eventService.getCommentsByEventId(id);

	}

	@PostMapping("/events/{id}/comments")
	public @ResponseBody CommentResponse postPlaceComment(@PathVariable long id, RequestEntity<Comment> requestEntity) {
		Comment newComment = requestEntity.getBody();
		if (newComment != null) {
			eventService.saveComment(newComment, id);
		}

		return eventService.getCommentsByEventId(id);
	}
	
	@PostMapping({ "/events/file-upload/single", "/{language}/events/file-upload/single" })
	public @ResponseBody boolean landingPageByTypes(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language, @RequestParam("file") MultipartFile[] files,
			@RequestParam("userId") long userId, @RequestParam("pageId") long pageId) {
		try {
			fileService.saveFiles(files, userId, LandingPageType.EVENT.getId(), pageId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}

	@GetMapping({ "/events/{id}/files" })
	public @ResponseBody List<MyFile> getFilesById(Model model, @PathVariable long id) {
		return fileService.getFilesByPageId(LandingPageType.EVENT.getId(), id);
	}
}

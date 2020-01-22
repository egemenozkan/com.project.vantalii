package com.project.web.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import org.springframework.util.CollectionUtils;
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
import com.project.api.data.enums.PeriodType;
import com.project.api.data.model.EventSession;
import com.project.api.data.model.autocomplete.AutocompleteResponse;
import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.CommentResponse;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;
import com.project.api.data.model.event.EventType;
import com.project.api.data.model.event.TimeTable;
import com.project.api.data.model.file.MyFile;
import com.project.web.model.AutocompleteRequest;
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
		if (startDate != null && endDate == null) {
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
			@PathVariable(required = false, name = "language") String language, @PathVariable String slug,
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

		if (page.getSlug() != null && !slug.equalsIgnoreCase(page.getSlug())) {
			StringBuilder redirectUrl = new StringBuilder("redirect:/");
			if (!(request.getHeader("host").contains("vantalii.ru") || "ru".equalsIgnoreCase(language))) {
				redirectUrl.append(page.getLanguage().getCode().toLowerCase()).append("/");
			}
			redirectUrl.append("events/").append(page.getSlug());

			if (logger.isWarnEnabled()) {
				logger.warn("::redirect status 301  {}-> {}", url, redirectUrl);
			}
			return new ModelAndView(redirectUrl.toString(), HttpStatus.PERMANENT_REDIRECT);
		}
		model.addAttribute("page", page);
		List<TimeTable> timeTables = eventService.getTimeTableByEventId(page.getEvent().getId());

		if (!CollectionUtils.isEmpty(timeTables)) {
			LocalDate TODAY = LocalDate.now();
			Map<LocalDate, List<EventSession>> dailyEventsMap = new TreeMap<>();
			for (TimeTable timeTable : timeTables) {
				/**
				 * Geçmiş etkinlikler gösterilmez (bugünden başlar)
				 */
				if (timeTable.getEndDate().isBefore(TODAY)) {
					continue;
				}
				/**
				 * Etkin takvimi geçmişi göstermez, daha önce başladıysa bugünden itibaren,
				 * gelecekte başlayacaksa ilk etkinlik gününden başlar.
				 **/
				LocalDate startDate = null;
				if (timeTable.getStartDate().compareTo(TODAY) < 1) {
					startDate = TODAY;
				} else {
					startDate = timeTable.getStartDate();
				}
				LocalDate endDate = null;
				if (timeTable.getEndDate().compareTo(TODAY.plusDays(7L)) > -1) {
					endDate = TODAY.plusDays(7L);
				} else {
					endDate = timeTable.getEndDate();
				}
				for (LocalDate date = startDate; date.compareTo(endDate) < 0; date = date.plusDays(1L)) {
					if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
						if (isMondays(timeTable)) {
							addSession(TODAY, dailyEventsMap, timeTable, date);
						} else {
							dailyEventsMap.put(date, Collections.emptyList());
						}
					} else if (date.getDayOfWeek() == DayOfWeek.TUESDAY) {
						if (isTuesday(timeTable)) {
							addSession(TODAY, dailyEventsMap, timeTable, date);
						} else {
							dailyEventsMap.put(date, Collections.emptyList());
						}
					} else if (date.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
						if (isWednesdays(timeTable)) {
							addSession(TODAY, dailyEventsMap, timeTable, date);
						} else {
							dailyEventsMap.put(date, Collections.emptyList());
						}
					} else if (date.getDayOfWeek() == DayOfWeek.THURSDAY) {
						if (isThursdays(timeTable)) {
							addSession(TODAY, dailyEventsMap, timeTable, date);
						} else {
							dailyEventsMap.put(date, Collections.emptyList());
						}
					} else if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
						if (isFridays(timeTable)) {
							addSession(TODAY, dailyEventsMap, timeTable, date);
						} else {
							dailyEventsMap.put(date, Collections.emptyList());
						}
					} else if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
						if (isSaturdays(timeTable)) {
							addSession(TODAY, dailyEventsMap, timeTable, date);
						} else {
							dailyEventsMap.put(date, Collections.emptyList());
						}
					} else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
						if (isSundays(timeTable)) {
							addSession(TODAY, dailyEventsMap, timeTable, date);
						} else {
							dailyEventsMap.put(date, Collections.emptyList());
						}
					}

				}
//					switch (date.getDayOfWeek()) {
//					case MONDAY:
//						if (timeTable.getPeriodType() == PeriodType.MONDAYS
//								|| timeTable.getPeriodType() == PeriodType.WEEKDAYS
//								|| timeTable.getPeriodType() == PeriodType.ALL
//								|| timeTable.getPeriodType() == PeriodType.ONEDAY) {
//							List<EventSession> sessions = dailyEventsMap.getOrDefault(date, new ArrayList<>());
//							EventSession session = new EventSession();
//							session.setDay(date.getDayOfMonth());
//							session.setDate(date);
//							session.setNow(false);
//							session.setStartTime(timeTable.getStartTime());
//							session.setEndTime(timeTable.getEndTime());
//							sessions.add(session);
//							dailyEventsMap.put(date, sessions);
//						}
//						break;
//					case TUESDAY:
//						if (timeTable.getPeriodType() == PeriodType.TUESDAYS
//								|| timeTable.getPeriodType() == PeriodType.WEEKDAYS
//								|| timeTable.getPeriodType() == PeriodType.ALL
//								|| timeTable.getPeriodType() == PeriodType.ONEDAY) {
//							List<EventSession> sessions = dailyEventsMap.getOrDefault(date, new ArrayList<>());
//							EventSession session = new EventSession();
//							session.setDay(date.getDayOfMonth());
//							session.setDate(date);
//							session.setNow(false);
//							session.setTime(timeTable.getStartTime());
//							sessions.add(session);
//							dailyEventsMap.put(date, sessions);
//						}
//						break;
//					case WEDNESDAY:
//						if (timeTable.getPeriodType() == PeriodType.WEDNESDAYS
//								|| timeTable.getPeriodType() == PeriodType.WEEKDAYS
//								|| timeTable.getPeriodType() == PeriodType.ALL
//								|| timeTable.getPeriodType() == PeriodType.ONEDAY) {
//							List<EventSession> sessions = dailyEventsMap.getOrDefault(date, new ArrayList<>());
//							EventSession session = new EventSession();
//							session.setDay(date.getDayOfMonth());
//							session.setDate(date);
//							session.setNow(false);
//							session.setTime(timeTable.getStartTime());
//							sessions.add(session);
//							dailyEventsMap.put(date, sessions);
//						}
//						break;
//					case THURSDAY:
//						if (timeTable.getPeriodType() == PeriodType.THURSDAYS
//								|| timeTable.getPeriodType() == PeriodType.WEEKDAYS
//								|| timeTable.getPeriodType() == PeriodType.ALL
//								|| timeTable.getPeriodType() == PeriodType.ONEDAY) {
//							List<EventSession> sessions = dailyEventsMap.getOrDefault(date, new ArrayList<>());
//							EventSession session = new EventSession();
//							session.setDay(date.getDayOfMonth());
//							session.setDate(date);
//							session.setNow(false);
//							session.setTime(timeTable.getStartTime());
//							sessions.add(session);
//							dailyEventsMap.put(date, sessions);
//						}
//						break;
//					case FRIDAY:
//						if (timeTable.getPeriodType() == PeriodType.FRIDAYS
//								|| timeTable.getPeriodType() == PeriodType.FRIDAYS_AND_SATURDAYS
//								|| timeTable.getPeriodType() == PeriodType.WEEKDAYS
//								|| timeTable.getPeriodType() == PeriodType.ALL
//								|| timeTable.getPeriodType() == PeriodType.ONEDAY) {
//							List<EventSession> sessions = dailyEventsMap.getOrDefault(date, new ArrayList<>());
//							EventSession session = new EventSession();
//							session.setDay(date.getDayOfMonth());
//							session.setDate(date);
//							session.setNow(false);
//							session.setTime(timeTable.getStartTime());
//							sessions.add(session);
//							dailyEventsMap.put(date, sessions);
//						}
//						break;
//					case SATURDAY:
//						if (timeTable.getPeriodType() == PeriodType.SATURDAYS
//								|| timeTable.getPeriodType() == PeriodType.FRIDAYS_AND_SATURDAYS
//								|| timeTable.getPeriodType() == PeriodType.WEEKENDS
//								|| timeTable.getPeriodType() == PeriodType.ALL
//								|| timeTable.getPeriodType() == PeriodType.ONEDAY) {
//							List<EventSession> sessions = dailyEventsMap.getOrDefault(date, new ArrayList<>());
//							EventSession session = new EventSession();
//							session.setDay(date.getDayOfMonth());
//							session.setDate(date);
//							session.setNow(false);
//							session.setTime(timeTable.getStartTime());
//							sessions.add(session);
//							dailyEventsMap.put(date, sessions);
//						}
//						break;
//					case SUNDAY:
//						if (timeTable.getPeriodType() == PeriodType.SUNDAYS
//								|| timeTable.getPeriodType() == PeriodType.WEEKENDS
//								|| timeTable.getPeriodType() == PeriodType.ALL
//								|| timeTable.getPeriodType() == PeriodType.ONEDAY) {
//							List<EventSession> sessions = dailyEventsMap.getOrDefault(date, new ArrayList<>());
//							EventSession session = new EventSession();
//							session.setDay(date.getDayOfMonth());
//							session.setDate(date);
//							session.setNow(false);
//							session.setTime(timeTable.getStartTime());
//							sessions.add(session);
//							dailyEventsMap.put(date, sessions);
//						}
//						break;
//					default:
////							List<EventSession> sessions = dailyEventsMap.getOrDefault(date, new ArrayList<>());
//						break;
//					}
			}

			model.addAttribute("dailyEvents", dailyEventsMap);
			logger.error("::logger {}", gson.toJson(dailyEventsMap));

		}

//		model.addAttribute("timeTables", timeTables);
//
		return new ModelAndView("events/detail");

	}

	private boolean isSundays(TimeTable timeTable) {
		return timeTable.getPeriodType() == PeriodType.SUNDAYS
				|| timeTable.getPeriodType() == PeriodType.WEEKENDS
				|| timeTable.getPeriodType() == PeriodType.ALL
				|| timeTable.getPeriodType() == PeriodType.ONEDAY;
	}

	private boolean isSaturdays(TimeTable timeTable) {
		return timeTable.getPeriodType() == PeriodType.SATURDAYS
				|| timeTable.getPeriodType() == PeriodType.FRIDAYS_AND_SATURDAYS
				|| timeTable.getPeriodType() == PeriodType.WEEKENDS
				|| timeTable.getPeriodType() == PeriodType.ALL
				|| timeTable.getPeriodType() == PeriodType.ONEDAY;
	}

	private boolean isFridays(TimeTable timeTable) {
		return timeTable.getPeriodType() == PeriodType.FRIDAYS
				|| timeTable.getPeriodType() == PeriodType.FRIDAYS_AND_SATURDAYS
				|| timeTable.getPeriodType() == PeriodType.WEEKDAYS
				|| timeTable.getPeriodType() == PeriodType.ALL
				|| timeTable.getPeriodType() == PeriodType.ONEDAY;
	}

	private boolean isThursdays(TimeTable timeTable) {
		return timeTable.getPeriodType() == PeriodType.THURSDAYS
				|| timeTable.getPeriodType() == PeriodType.WEEKDAYS
				|| timeTable.getPeriodType() == PeriodType.ALL
				|| timeTable.getPeriodType() == PeriodType.ONEDAY;
	}

	private boolean isWednesdays(TimeTable timeTable) {
		return timeTable.getPeriodType() == PeriodType.WEDNESDAYS
				|| timeTable.getPeriodType() == PeriodType.WEEKDAYS
				|| timeTable.getPeriodType() == PeriodType.ALL
				|| timeTable.getPeriodType() == PeriodType.ONEDAY;
	}

	private boolean isTuesday(TimeTable timeTable) {
		return timeTable.getPeriodType() == PeriodType.TUESDAYS
				|| timeTable.getPeriodType() == PeriodType.WEEKDAYS
				|| timeTable.getPeriodType() == PeriodType.ALL
				|| timeTable.getPeriodType() == PeriodType.ONEDAY;
	}

	private boolean isMondays(TimeTable timeTable) {
		return timeTable.getPeriodType() == PeriodType.MONDAYS
				|| timeTable.getPeriodType() == PeriodType.WEEKDAYS
				|| timeTable.getPeriodType() == PeriodType.ALL
				|| timeTable.getPeriodType() == PeriodType.ONEDAY;
	}

	private void addSession(LocalDate TODAY, Map<LocalDate, List<EventSession>> dailyEventsMap, TimeTable timeTable,
			LocalDate date) {
		List<EventSession> sessions = dailyEventsMap.getOrDefault(date, new ArrayList<>());

		EventSession session = new EventSession();
		session.setDay(date.getDayOfMonth());
		session.setDate(date);
		session.setNow(false);
		session.setToday(TODAY.equals(date));
		session.setStartTime(timeTable.getStartTime());
		session.setEndTime(timeTable.getEndTime());
		sessions.add(session);
		dailyEventsMap.put(date, sessions);
	}

	@GetMapping({ "/events/m/{slug}", "/{language}/events/m/{slug}" })
	public ModelAndView landingPageByType(Model model, HttpServletRequest request,
			@PathVariable(required = false, name = "language") String language, @PathVariable String slug,
			@RequestParam(required = false, defaultValue = "0", name = "district") String[] districts,
			@RequestParam(required = false, defaultValue = "0", name = "region") String[] regions) {

		EventRequest eventRequest = new EventRequest();
		eventRequest.setLanguage(Language.getByCode((language == null) ? "RU" : language));
		eventRequest.setType(EventType.getBySlug(slug));
		eventRequest.setRegions(regions);
		eventRequest.setDistricts(districts);
		List<EventLandingPage> pages = eventService.getEventLandingPages(eventRequest);

		if (logger.isWarnEnabled() && CollectionUtils.isEmpty(pages)) {
			logger.warn("::landingPageByType({}) response is empty", slug);
		}

		model.addAttribute("pages", pages);

		return new ModelAndView("events/list");
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
			@PathVariable(required = false, name = "language") String language,
			@RequestParam("file") MultipartFile[] files, @RequestParam("userId") long userId,
			@RequestParam("pageId") long pageId) {
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

	@GetMapping({ "/events/autocomplete" })
	public @ResponseBody AutocompleteResponse callAutocomplete(@RequestParam String query,
			@RequestParam(defaultValue = "s", required = false) String language) {
		return eventService.callAutocomplete(new AutocompleteRequest(query, Language.getByCode(language)));
	}
}

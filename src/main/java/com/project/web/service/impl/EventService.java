package com.project.web.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.project.api.data.enums.PeriodType;
import com.project.api.data.model.autocomplete.AutocompleteResponse;
import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.CommentResponse;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;
import com.project.api.data.model.event.TimeTable;
import com.project.web.model.AutocompleteRequest;
import com.project.web.service.IEventService;

@Service
public class EventService extends BaseApiService implements IEventService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${security.api.auth-server-url}")
	private String authServerUrl;

	final Logger logger = LoggerFactory.getLogger(EventService.class);

	private static final String CACHE_KEY = "EVENT";

	@Autowired
	Gson gson;

	@Override
	public EventLandingPage getEventLandingPage(long id, String language, long timeTableId) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/events/{id}/pages");

		if (language != null) {
			endpoint.append("?language=");
			endpoint.append(language);
		}
		if (language == null && timeTableId > 0) {
			endpoint.append("?");
		} else if (language != null && timeTableId > 0) {
			endpoint.append("&");
		}
		if (timeTableId > 0) {
			endpoint.append("timeTableId=");
			endpoint.append(timeTableId);
		}
		Object cacheValue = null; // redisTemplate.opsForHash().get(CACHE_KEY, endpoint.toString());

		if (cacheValue != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("::cache getEventLandingPage");
			}
			return (EventLandingPage) cacheValue;
		} else {
			EventLandingPage page = (EventLandingPage) getObject(endpoint.toString(), EventLandingPage.class, id);
			if (page != null) {
				redisTemplate.opsForHash().put(CACHE_KEY, endpoint.toString(), page);
			}
			return page;
		}

	}

	@Override
	public List<EventLandingPage> getEventLandingPages(EventRequest eventRequest) {
		UriComponentsBuilder endpoint = UriComponentsBuilder.fromUriString(authServerUrl + "/api/v1/events/pages");
		if (eventRequest.getLanguage() != null) {
			endpoint.queryParam("language", eventRequest.getLanguage().getCode());
		}

		if (eventRequest.getType() != null) {
			endpoint.queryParam("type", eventRequest.getType().getId());
		}

		if (eventRequest.getStatus() != null) {
			endpoint.queryParam("status", eventRequest.getStatus().getId());
		}
		if (eventRequest.getDistricts() != null) {
			endpoint.queryParam("districts", String.join(",", eventRequest.getDistricts()));
		}
		if (eventRequest.getRegions() != null) {
			endpoint.queryParam("regions", String.join(",", eventRequest.getRegions()));
		}
		Object cacheValue = redisTemplate.opsForHash().get(CACHE_KEY, endpoint.toString());

		if (cacheValue != null) {
			logger.info("::cache getEventLandingPages");
			return (List<EventLandingPage>) cacheValue;
		} else {
			List<EventLandingPage> pages = getList(endpoint.toUriString(),
					new ParameterizedTypeReference<List<EventLandingPage>>() {
					});
			if (pages != null) {
				redisTemplate.opsForHash().put(CACHE_KEY, endpoint.toString(), pages);
			}
			return pages;
		}
	}

	@Override
	public List<Event> getEvents(EventRequest eventRequest) {
		UriComponentsBuilder endpoint = UriComponentsBuilder.fromUriString(authServerUrl + "/api/v1/events");

		if (eventRequest.getLanguage() != null) {
			endpoint.queryParam("language", eventRequest.getLanguage().getCode());
		}
		if (eventRequest.getLimit() > 0) {
			endpoint.queryParam("limit", eventRequest.getLimit());
		}
		if (eventRequest.getType() != null) {
			endpoint.queryParam("type", eventRequest.getType().getId());
		}
		if (eventRequest.getTypes() != null) {
			endpoint.queryParam("types", String.join(",", eventRequest.getTypes()));
		}
		if (eventRequest.getRandom() != null && eventRequest.getRandom()) {
			endpoint.queryParam("random", eventRequest.getRandom());
		}
		if (eventRequest.getStartDate() != null) {
			endpoint.queryParam("startDate", eventRequest.getStartDate());
		}
		if (eventRequest.getEndDate() != null) {
			endpoint.queryParam("endDate", eventRequest.getEndDate());
		}
		if (eventRequest.getPlaceId() > 0) {
			endpoint.queryParam("placeId", eventRequest.getPlaceId());
		}
		if (eventRequest.getPeriodType() != null) {
			endpoint.queryParam("periodType", eventRequest.getPeriodType().getId());
		}

		if (eventRequest.getStatus() != null) {
			endpoint.queryParam("status", eventRequest.getStatus().getId());
		}
		if (eventRequest.getDistricts() != null) {
			endpoint.queryParam("districts", String.join(",", eventRequest.getDistricts()));
		}
		if (eventRequest.getRegions() != null) {
			endpoint.queryParam("regions", String.join(",", eventRequest.getRegions()));
		}
		Object cacheValue = null; // redisTemplate.opsForHash().get(CACHE_KEY, endpoint.toString());

		if (cacheValue != null) {
			logger.info("::cache getEvents");
			return (List<Event>) cacheValue;
		} else {
			List<Event> events = getList(endpoint.toUriString(), new ParameterizedTypeReference<List<Event>>() {
			});
			if (events != null) {
				redisTemplate.opsForHash().put(CACHE_KEY, endpoint.toString(), events);
			}
			return events;
		}

	}

	@Override
	public Map<LocalDate, List<Event>> getEventsMap(EventRequest eventRequest) {

		if (eventRequest != null && eventRequest.getStartDate() != null && eventRequest.getEndDate() == null) {
			eventRequest.setEndDate(eventRequest.getStartDate());
		}
		if (eventRequest.getStartDate() == null && eventRequest.getEndDate() == null) {
			return null;
		}
		List<Event> events = getEvents(eventRequest);

		if (logger.isDebugEnabled()) {
			logger.debug("::getEvents request: {}, response: {}", gson.toJson(eventRequest), gson.toJson(events));
		}
		logger.error("::getEvents request: {}, response: {}", gson.toJson(eventRequest), gson.toJson(events));

		Map<LocalDate, List<Event>> eventsMap = new TreeMap<>();
		for (LocalDate eventDate = eventRequest.getStartDate(); eventDate
				.compareTo(eventRequest.getEndDate()) < 1; eventDate = eventDate.plusDays(1L)) {
			LocalDate mapKey = eventDate;
			for (Iterator<Event> iterator = events.iterator(); iterator.hasNext();) {
				Event event = iterator.next();
				List<Event> mapEvents = eventsMap.get(mapKey);
				if (eventDate.compareTo(event.getStartDate()) < 0 || eventDate.compareTo(event.getEndDate()) > 0) {
					continue;
				}
				switch (eventDate.getDayOfWeek()) {
				case MONDAY:
					if (event.getPeriodType() == PeriodType.MONDAYS || event.getPeriodType() == PeriodType.WEEKDAYS
							|| event.getPeriodType() == PeriodType.ALL || event.getPeriodType() == PeriodType.ONEDAY) {

						if (mapEvents == null) {
							mapEvents = new ArrayList<>();
							mapEvents.add(event);
							eventsMap.put(mapKey, mapEvents);
						} else {
							if (!mapEvents.contains(event)) {
								mapEvents.add(event);
							}
						}

					}
					break;
				case TUESDAY:
					if (event.getPeriodType() == PeriodType.TUESDAYS || event.getPeriodType() == PeriodType.WEEKDAYS
							|| event.getPeriodType() == PeriodType.ALL || event.getPeriodType() == PeriodType.ONEDAY) {
						if (mapEvents == null) {
							mapEvents = new ArrayList<>();
							mapEvents.add(event);
							eventsMap.put(mapKey, mapEvents);
						} else {
							if (!mapEvents.contains(event)) {
								mapEvents.add(event);
							}
						}
					}
					break;
				case WEDNESDAY:
					if (event.getPeriodType() == PeriodType.WEDNESDAYS || event.getPeriodType() == PeriodType.WEEKDAYS
							|| event.getPeriodType() == PeriodType.ALL || event.getPeriodType() == PeriodType.ONEDAY) {
						if (mapEvents == null) {
							mapEvents = new ArrayList<>();
							mapEvents.add(event);
							eventsMap.put(mapKey, mapEvents);
						} else {
							if (!mapEvents.contains(event)) {
								mapEvents.add(event);
							}
						}
					}
					break;
				case THURSDAY:
					if (event.getPeriodType() == PeriodType.THURSDAYS || event.getPeriodType() == PeriodType.WEEKDAYS
							|| event.getPeriodType() == PeriodType.ALL || event.getPeriodType() == PeriodType.ONEDAY) {
						if (mapEvents == null) {
							mapEvents = new ArrayList<>();
							mapEvents.add(event);
							eventsMap.put(mapKey, mapEvents);
						} else {
							if (!mapEvents.contains(event)) {
								mapEvents.add(event);
							}
						}
					}
					break;
				case FRIDAY:
					if (event.getPeriodType() == PeriodType.FRIDAYS
							|| event.getPeriodType() == PeriodType.FRIDAYS_AND_SATURDAYS
							|| event.getPeriodType() == PeriodType.WEEKDAYS || event.getPeriodType() == PeriodType.ALL
							|| event.getPeriodType() == PeriodType.ONEDAY) {
						if (mapEvents == null) {
							mapEvents = new ArrayList<>();
							mapEvents.add(event);
							eventsMap.put(mapKey, mapEvents);
						} else {
							if (!mapEvents.contains(event)) {
								mapEvents.add(event);
							}
						}
					}
					break;
				case SATURDAY:
					if (event.getPeriodType() == PeriodType.SATURDAYS
							|| event.getPeriodType() == PeriodType.FRIDAYS_AND_SATURDAYS
							|| event.getPeriodType() == PeriodType.WEEKENDS || event.getPeriodType() == PeriodType.ALL
							|| event.getPeriodType() == PeriodType.ONEDAY) {
						if (mapEvents == null) {
							mapEvents = new ArrayList<>();
							mapEvents.add(event);
							eventsMap.put(mapKey, mapEvents);
						} else {
							if (!mapEvents.contains(event)) {
								mapEvents.add(event);
							}
						}
					}
					break;
				case SUNDAY:
					if (event.getPeriodType() == PeriodType.SUNDAYS || event.getPeriodType() == PeriodType.WEEKENDS
							|| event.getPeriodType() == PeriodType.ALL || event.getPeriodType() == PeriodType.ONEDAY) {
						if (mapEvents == null) {
							mapEvents = new ArrayList<>();
							mapEvents.add(event);
							eventsMap.put(mapKey, mapEvents);
						} else {
							if (!mapEvents.contains(event)) {
								mapEvents.add(event);
							}
						}
					}
					break;
				default:
					break;
				}

			}
		}

		return eventsMap;
	}

	@Override
	public CommentResponse getCommentsByEventId(long eventId) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/events/{eventId}/comments");
		return (CommentResponse) getObject(endpoint.toString(), CommentResponse.class, eventId);
	}

	@Override
	public long saveComment(Comment comment, long eventId) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/events/{eventId}/comments");

		return (long) postObject(endpoint.toString(), comment, Long.class, eventId);
	}

	@Override
	public AutocompleteResponse callAutocomplete(AutocompleteRequest autocompleteRequest) {
		UriComponentsBuilder endpoint = UriComponentsBuilder
				.fromUriString(authServerUrl + "/api/v1/events/autocomplete");

		if (autocompleteRequest.getLanguage() != null) {
			endpoint.queryParam("language", autocompleteRequest.getLanguage().getCode());
		}
		endpoint.queryParam("query", autocompleteRequest.getQuery());
		return (AutocompleteResponse) getObject(endpoint.toUriString(), AutocompleteResponse.class);
	}

	@Override
	public List<TimeTable> getTimeTableByEventId(long eventId) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/events/" + eventId + "/time-table");
		return getList(endpoint.toString(), new ParameterizedTypeReference<List<TimeTable>>() {
		});
	}

}

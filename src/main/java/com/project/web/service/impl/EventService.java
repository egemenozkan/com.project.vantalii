package com.project.web.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.project.api.data.enums.EventPeriodType;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;
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
	public EventLandingPage getEventLandingPage(long id, String language) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/events/{id}/pages");
		if (language != null) {
			endpoint.append("?language=");
			endpoint.append(language);
		}
		return (EventLandingPage) getObject(endpoint.toString(), EventLandingPage.class, id);
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

		Object cacheValue = redisTemplate.opsForHash().get(CACHE_KEY, endpoint.toString());

		if (cacheValue != null) {
			logger.info("::cache getEventLandingPages");
			return (List<EventLandingPage>) cacheValue;
		} else {
			List<EventLandingPage> pages = getList(endpoint.toUriString());
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
			endpoint.queryParam("types", String.join("," ,eventRequest.getTypes()));
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

		Object cacheValue = redisTemplate.opsForHash().get(CACHE_KEY, endpoint.toString());

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
	public Map<String, List<Event>> getEventsMap(EventRequest eventRequest) {
		List<Event> events = getEvents(eventRequest);

		Map<String, List<Event>> eventsMap = new HashMap<>();
		for (LocalDate eventDate = eventRequest.getStartDate(); eventDate
				.compareTo(eventRequest.getEndDate()) < 1; eventDate = eventDate.plusDays(1L)) {
			String mapKey = eventDate.toString();
			for (Iterator<Event> iterator = events.iterator(); iterator.hasNext();) {
				Event event = iterator.next();
				List<Event> mapEvents = eventsMap.get(mapKey);
				if (eventDate.compareTo(event.getStartDate()) < 0 || eventDate.compareTo(event.getEndDate()) > 0) {
					continue;
				}
				switch (eventDate.getDayOfWeek()) {
				case MONDAY:
					if (event.getPeriodType() == EventPeriodType.MONDAYS
							|| event.getPeriodType() == EventPeriodType.WEEKDAYS
							|| event.getPeriodType() == EventPeriodType.ALL
							|| event.getPeriodType() == EventPeriodType.ONEDAY) {

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
					if (event.getPeriodType() == EventPeriodType.TUESDAYS
							|| event.getPeriodType() == EventPeriodType.WEEKDAYS
							|| event.getPeriodType() == EventPeriodType.ALL
							|| event.getPeriodType() == EventPeriodType.ONEDAY) {
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
					if (event.getPeriodType() == EventPeriodType.WEDNESDAYS
							|| event.getPeriodType() == EventPeriodType.WEEKDAYS
							|| event.getPeriodType() == EventPeriodType.ALL
							|| event.getPeriodType() == EventPeriodType.ONEDAY) {
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
					if (event.getPeriodType() == EventPeriodType.THURSDAYS
							|| event.getPeriodType() == EventPeriodType.WEEKDAYS
							|| event.getPeriodType() == EventPeriodType.ALL
							|| event.getPeriodType() == EventPeriodType.ONEDAY) {
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
					if (event.getPeriodType() == EventPeriodType.FRIDAYS
							|| event.getPeriodType() == EventPeriodType.FRIDAYS_AND_SATURDAYS
							|| event.getPeriodType() == EventPeriodType.WEEKDAYS
							|| event.getPeriodType() == EventPeriodType.ALL
							|| event.getPeriodType() == EventPeriodType.ONEDAY) {
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
					if (event.getPeriodType() == EventPeriodType.SATURDAYS
							|| event.getPeriodType() == EventPeriodType.FRIDAYS_AND_SATURDAYS
							|| event.getPeriodType() == EventPeriodType.WEEKENDS
							|| event.getPeriodType() == EventPeriodType.ALL
							|| event.getPeriodType() == EventPeriodType.ONEDAY) {
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
					if (event.getPeriodType() == EventPeriodType.SUNDAYS
							|| event.getPeriodType() == EventPeriodType.WEEKENDS
							|| event.getPeriodType() == EventPeriodType.ALL
							|| event.getPeriodType() == EventPeriodType.ONEDAY) {
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

}

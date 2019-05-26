package com.project.web.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.api.data.enums.EventType;
import com.project.api.data.enums.Language;
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

	final Logger logger = LoggerFactory.getLogger(PlaceService.class);
	
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
//		if (eventRequest.getLimit() > 0) {
//			endpoint.queryParam("limit", eventRequest.getLimit());
//		}
		if (eventRequest.getType() != null) {
			endpoint.queryParam("type", eventRequest.getType().getId());
		}
//		if (eventRequest.getRandom() != null && eventRequest.getRandom()) {
//			endpoint.queryParam("random", eventRequest.getRandom());
//		}

		Object cacheValue = redisTemplate.opsForHash().get("EVENT", endpoint.toString());

		if (cacheValue != null) {
			logger.info("::cache getEventLandingPages");
			return (List<EventLandingPage>) cacheValue;
		} else {
			List pages = getList(endpoint.toUriString(), null);
			if (pages != null) {
				redisTemplate.opsForHash().put("EVENT", endpoint.toString(), pages);
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
		
		
		return getList(endpoint.toUriString(), null);
	
	}

}

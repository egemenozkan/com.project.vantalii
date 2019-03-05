package com.project.web.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.api.data.model.event.EventLandingPage;
import com.project.web.service.IEventService;

@Service
public class EventService extends BaseApiService implements IEventService {
	@Value("${security.api.auth-server-url}")
	private String authServerUrl;

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

}

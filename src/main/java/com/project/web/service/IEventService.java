package com.project.web.service;

import com.project.api.data.model.event.EventLandingPage;

public interface IEventService {
	EventLandingPage getEventLandingPage(long id, String language);

}

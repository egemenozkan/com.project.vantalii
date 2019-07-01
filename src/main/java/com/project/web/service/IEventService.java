package com.project.web.service;

import java.util.List;
import java.util.Map;

import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;

public interface IEventService {
	EventLandingPage getEventLandingPage(long id, String language);
	List<EventLandingPage> getEventLandingPages(EventRequest eventRequest);
	List<Event> getEvents(EventRequest eventRequest);
	Map<String,List<Event>> getEventsMap(EventRequest eventRequest);


}

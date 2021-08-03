package com.vantalii.web.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.project.api.data.model.autocomplete.AutocompleteResponse;
import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.CommentResponse;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;
import com.project.api.data.model.event.TimeTable;
import com.vantalii.web.model.AutocompleteRequest;

public interface IEventService {
	EventLandingPage getEventLandingPage(long id, String language, long timeTableId);
	List<EventLandingPage> getEventLandingPages(EventRequest eventRequest);
	List<Event> getEvents(EventRequest eventRequest);
	Map<LocalDate,List<Event>> getEventsMap(EventRequest eventRequest);
	CommentResponse getCommentsByEventId(long eventId);
	long saveComment(Comment comment, long eventId);
	AutocompleteResponse callAutocomplete(AutocompleteRequest autocompleteRequest);
	List<TimeTable> getTimeTableByEventId(long eventId);
}

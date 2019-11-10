package com.project.web.service;

import java.util.List;
import java.util.Map;

import com.project.api.data.model.autocomplete.AutocompleteResponse;
import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.CommentResponse;
import com.project.api.data.model.event.Event;
import com.project.api.data.model.event.EventLandingPage;
import com.project.api.data.model.event.EventRequest;
import com.project.web.model.AutocompleteRequest;

public interface IEventService {
	EventLandingPage getEventLandingPage(long id, String language, long timeTableId);
	List<EventLandingPage> getEventLandingPages(EventRequest eventRequest);
	List<Event> getEvents(EventRequest eventRequest);
	Map<String,List<Event>> getEventsMap(EventRequest eventRequest);
	CommentResponse getCommentsByEventId(long eventId);
	long saveComment(Comment comment, long eventId);
	AutocompleteResponse callAutocomplete(AutocompleteRequest autocompleteRequest);
}

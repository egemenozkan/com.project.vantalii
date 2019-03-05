package com.project.web.service;

import java.util.List;

import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.PlaceCommentResponse;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;

public interface IPlaceService {
	List<PlaceLandingPage> getPlaceLandingPages(PlaceRequest placeRequest);
	PlaceLandingPage getPlaceLandingPage(long id, String language);
	PlaceCommentResponse getPlaceCommentsByPlaceId(long id);
	long savePlaceComment(Comment comment, long placeId);

}

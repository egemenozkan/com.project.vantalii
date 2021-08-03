package com.vantalii.web.service;

import java.util.List;

import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.CommentResponse;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;

public interface IPlaceService {
	List<PlaceLandingPage> getPlaceLandingPages(PlaceRequest placeRequest);
	PlaceLandingPage getPlaceLandingPage(long id, String language);
	CommentResponse getCommentsByPlaceId(long placeId);
	long saveComment(Comment comment, long placeId);

}

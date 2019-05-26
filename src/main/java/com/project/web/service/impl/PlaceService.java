package com.project.web.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.PlaceCommentResponse;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;
import com.project.web.service.IPlaceService;

@Service
@SuppressWarnings("unchecked")
public class PlaceService extends BaseApiService implements IPlaceService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
   
	@Value("${security.api.auth-server-url}")
	private String authServerUrl;
    
	final Logger logger = LoggerFactory.getLogger(PlaceService.class);

	
	@Override
	public List<PlaceLandingPage> getPlaceLandingPages(PlaceRequest placeRequest) {
		UriComponentsBuilder endpoint = UriComponentsBuilder.fromUriString(authServerUrl + "/api/v1/places/pages");
		if (placeRequest.getLanguage() != null) {
			endpoint.queryParam("language", placeRequest.getLanguage().getCode());
		}
		if (placeRequest.getLimit() > 0) {
			endpoint.queryParam("limit", placeRequest.getLimit());
		}
		if (placeRequest.getMainType() != null) {
			endpoint.queryParam("mainType", placeRequest.getMainType().getId());
		}
		if (placeRequest.getType() != null) {
			endpoint.queryParam("type", placeRequest.getType().getId());
		}
		if (placeRequest.getRandom() != null && placeRequest.getRandom()) {
			endpoint.queryParam("random", placeRequest.getRandom());
		}

		Object cacheValue = redisTemplate.opsForHash().get("PLACE", endpoint.toString());

		if (cacheValue != null) {
			logger.info("::cache getPlaceLandingPages");
			return (List<PlaceLandingPage>) cacheValue;
		} else {
			List pages = getList(endpoint.toUriString(), null);
			if (pages != null) {
				redisTemplate.opsForHash().put("PLACE", endpoint.toString(), pages);
			}
			return pages;
		}
	}

	@Override
	public PlaceLandingPage getPlaceLandingPage(long id, String language) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/places/{id}/pages");
		if (language != null) {
			endpoint.append("?language=");
			endpoint.append(language);
		}

		String cacheKey = "place_" + id + "_" + language ;
		return (PlaceLandingPage) getObject(endpoint.toString(), PlaceLandingPage.class, id);

//		Object cacheValue = redisTemplate.opsForHash().get("PLACE", cacheKey);
//
//		if (cacheValue != null) {
//			logger.info("::cache getPlaceLandingPage id: {} language: {}", id, language);
//			return (PlaceLandingPage) cacheValue;
//		} else {
//			PlaceLandingPage page = (PlaceLandingPage) getObject(endpoint.toString(), PlaceLandingPage.class, id);
//			if (page != null) {
//				redisTemplate.opsForHash().put("PLACE", cacheKey, page);
//			}
//			return page;
//		}
	}

	@Override
	public PlaceCommentResponse getPlaceCommentsByPlaceId(long id) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/places/{id}/comments");
		return (PlaceCommentResponse) getObject(endpoint.toString(), PlaceCommentResponse.class, id);
	}

	@Override
	public long savePlaceComment(Comment comment, long id) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/places/{id}/comments");

		return (long) postObject(endpoint.toString(), comment, Long.class, id);
	}

}

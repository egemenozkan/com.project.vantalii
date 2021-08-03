package com.vantalii.web.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.api.data.model.comment.Comment;
import com.project.api.data.model.comment.CommentResponse;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;
import com.vantalii.web.service.IPlaceService;

@Service
@SuppressWarnings("unchecked")
public class PlaceService extends BaseApiService implements IPlaceService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
   
	@Value("${security.api.auth-server-url}")
	private String authServerUrl;
    
	final Logger logger = LoggerFactory.getLogger(PlaceService.class);
	
	private boolean cacheAvailable = true;
	
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
		if (placeRequest.isHideAddress()) {
			endpoint.queryParam("hideAddress", placeRequest.isHideAddress());
		}
		if (placeRequest.isHideContact()) {
			endpoint.queryParam("hideContact", placeRequest.isHideContact());
		}
		if (placeRequest.isHideContent()) {
			endpoint.queryParam("hideContent", placeRequest.isHideContent());
		}
		if (placeRequest.isHideImages()) {
			endpoint.queryParam("hideImages", placeRequest.isHideImages());
		}
		if (placeRequest.isHideMainImage()) {
			endpoint.queryParam("hideMainImage", placeRequest.isHideMainImage());
		}
		if (placeRequest.getDistricts() != null && placeRequest.getDistricts().length > 0) {
			endpoint.queryParam("districts", String.join(",", placeRequest.getDistricts()));
		}
		if (placeRequest.getRegions() != null && placeRequest.getRegions().length > 0) {
			endpoint.queryParam("regions", String.join(",", placeRequest.getRegions()));
		}
		
		Object cacheValue = redisTemplate.opsForHash().get("PLACE", endpoint.toString());

		if (cacheValue != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("::cache getPlaceLandingPages");
			}
			return (List<PlaceLandingPage>) cacheValue;
		} else {
			List pages = getList(endpoint.toUriString(), new ParameterizedTypeReference<List<PlaceLandingPage>>() {});
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
		PlaceLandingPage page = (PlaceLandingPage) getObject(endpoint.toString(), PlaceLandingPage.class, id);
return page;
//		Object cacheValue = redisTemplate.opsForHash().get("PLACE", cacheKey);
//
//		if (cacheAvailable && cacheValue != null) {
//			logger.info("::cache getPlaceLandingPage id: {} language: {}", id, language);
//			return (PlaceLandingPage) cacheValue;
//		} else {
//			PlaceLandingPage page = (PlaceLandingPage) getObject(endpoint.toString(), PlaceLandingPage.class, id);
//			if (cacheAvailable && page != null) {
//				redisTemplate.opsForHash().put("PLACE", cacheKey, page);
//			}
//			return page;
//		}
	}

	@Override
	public CommentResponse getCommentsByPlaceId(long placeId) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/events/{placeId}/comments");
		return (CommentResponse) getObject(endpoint.toString(), CommentResponse.class, placeId);
	}

	@Override
	public long saveComment(Comment comment, long placeId) {
		StringBuilder endpoint = new StringBuilder(authServerUrl);
		endpoint.append("/api/v1/events/{placeId}/comments");

		return (long) postObject(endpoint.toString(), comment, Long.class, placeId);
	}

	public boolean isCacheAvailable() {
		return cacheAvailable;
	}

	public void setCacheAvailable(boolean cacheAvailable) {
		this.cacheAvailable = cacheAvailable;
	}

}

package com.project.web.service.impl;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Repository;

import com.project.api.data.model.event.Event;

@Repository
public abstract class BaseApiService<T> {

	@Autowired
	private OAuth2RestTemplate restTemplate;

	public List<?> getList(String endpoint, Object... parameters) {
		List<?> result = null;
		if (parameters == null || parameters.length == 0) {
			try {
				result = restTemplate.getForObject(endpoint, List.class);
			} catch (Exception exception) {
				exception.getLocalizedMessage();
			}
		} else {
			result = restTemplate.getForObject(endpoint, List.class, parameters);
		}

		return result;
	}

	public List<?> getList(URI endpoint, Object... parameters) {
		List<?> result = null;
		if (parameters == null || parameters.length == 0) {
			try {
				result = restTemplate.getForObject(endpoint, List.class);
			} catch (Exception exception) {
				exception.getLocalizedMessage();
			}
		} else {
			result = restTemplate.getForObject(endpoint.toString(), List.class, parameters);
		}

		return result;
	}

	public List<Event> getEventList(String url) {
		List<Event> result = Collections.emptyList();
		ResponseEntity<List<Event>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Event>>() {
				});
		if (responseEntity.hasBody()) {
			result = responseEntity.getBody();
		}

		return result;
	}
	
	public List<T> getList(String url, ParameterizedTypeReference<List<T>> ptr) {
		List<T> result = Collections.emptyList();
		ResponseEntity<List<T>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, ptr);
		if (responseEntity.hasBody()) {
			result = responseEntity.getBody();
		}

		return result;
	}

	public Object postObject(String endpoint, Object parameter, Class<T> responseType) {
		ResponseEntity<T> responseEntity = restTemplate.postForEntity(endpoint, parameter, responseType);
		T responseBody = responseEntity.getBody();
		return responseBody;
	}

	public Object patchObject(String endpoint, Object requestObject, Class<T> responseType) {
		T responseEntity = restTemplate.patchForObject(endpoint, requestObject, responseType);

		return responseEntity;
	}

	public Object postObject(String endpoint, Object request, Class<T> responseType, Object... parameters) {
		ResponseEntity<T> responseEntity = restTemplate.postForEntity(endpoint, request, responseType, parameters);
		T responseBody = responseEntity.getBody();
		return responseBody;
	}

	public Object getObject(String endpoint, Class<T> responseType, Object... parameters) {
		ResponseEntity<T> responseEntity = null;
		if (parameters == null || parameters.length == 0) {
			try {
				responseEntity = restTemplate.getForEntity(endpoint, responseType);
			} catch (Exception exception) {
				exception.getLocalizedMessage();
			}
		} else {
			responseEntity = restTemplate.getForEntity(endpoint, responseType, parameters);
		}
		if (responseEntity == null) {
			return null;
		}

		return responseEntity.getBody();
	}

	public Object getObject(URI endpoint, Class<T> responseType, Object... parameters) {
		ResponseEntity<T> responseEntity = null;
		if (parameters == null || parameters.length == 0) {
			try {
				responseEntity = restTemplate.getForEntity(endpoint, responseType);
			} catch (Exception exception) {
				exception.getLocalizedMessage();
			}
		} else {
			responseEntity = restTemplate.getForEntity(endpoint.toString(), responseType, parameters);
		}
		if (responseEntity == null) {
			return null;
		}

		return responseEntity.getBody();
	}

}

package com.project.web.service.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.project.web.model.AutocompleteItem;
import com.project.web.model.AutocompleteRequest;
import com.project.web.model.AutocompleteResponse;
import com.project.web.service.IDatapoolService;

@SuppressWarnings("rawtypes")
@Service
public class DatapoolService extends BaseApiService implements IDatapoolService {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${security.api.auth-server-url}")
	private String authServerUrl;

	final Logger logger = LoggerFactory.getLogger(DatapoolService.class);

	@Override
	public AutocompleteResponse getAutocompleteResponse(AutocompleteRequest autocompleteRequest) {
//		
//		
//		StringBuilder endpoint = new StringBuilder(authServerUrl);
//		endpoint.append("/api/v1/events/{id}/pages");
//		return null;
		
		AutocompleteResponse autocompleteResponse = new AutocompleteResponse();
		autocompleteResponse.setSuccess(true);
		List<AutocompleteItem> items = Arrays.asList(new AutocompleteItem("Deneme", "Deneme", "d", "url"), new AutocompleteItem("Deneme Bura 2",  "Deneme Bura 2", "d2", "url"));
		autocompleteResponse.setItems(items);
		
		return autocompleteResponse;
		
		
		
	}
}

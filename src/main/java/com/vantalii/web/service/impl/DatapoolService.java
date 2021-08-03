package com.vantalii.web.service.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.api.data.model.autocomplete.AutocompleteResponse;
import com.vantalii.web.model.AutocompleteRequest;
import com.vantalii.web.service.IDatapoolService;

@SuppressWarnings("rawtypes")
@Service
public class DatapoolService extends BaseApiService implements IDatapoolService {

	@Value("${security.api.auth-server-url}")
	private String authServerUrl;

	final Logger logger = LoggerFactory.getLogger(DatapoolService.class);

	@Override
	public AutocompleteResponse getAutocompleteResponse(AutocompleteRequest autocompleteRequest) throws URISyntaxException {
		URI endpoint = new URI(authServerUrl + "/api/v1/autocomplete");
		return (AutocompleteResponse) getResponse(endpoint, autocompleteRequest, AutocompleteResponse.class);
		
		
	}
}

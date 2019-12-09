package com.project.web.service;

import java.net.URISyntaxException;

import com.project.web.model.AutocompleteRequest;
import com.project.web.model.AutocompleteResponse;

public interface IDatapoolService {
	
	AutocompleteResponse getAutocompleteResponse(AutocompleteRequest autocompleteRequest) throws URISyntaxException;
	
}

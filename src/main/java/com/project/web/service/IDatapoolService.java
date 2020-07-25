package com.project.web.service;

import java.net.URISyntaxException;

import com.project.api.data.model.autocomplete.AutocompleteResponse;
import com.project.web.model.AutocompleteRequest;

public interface IDatapoolService {
	
	AutocompleteResponse getAutocompleteResponse(AutocompleteRequest autocompleteRequest) throws URISyntaxException;
	
}

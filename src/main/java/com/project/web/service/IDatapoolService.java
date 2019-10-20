package com.project.web.service;

import com.project.web.model.AutocompleteRequest;
import com.project.web.model.AutocompleteResponse;

public interface IDatapoolService {
	
	AutocompleteResponse getAutocompleteResponse(AutocompleteRequest autocompleteRequest);
	
}

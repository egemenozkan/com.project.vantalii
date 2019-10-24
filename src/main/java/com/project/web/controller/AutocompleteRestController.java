package com.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.web.model.AutocompleteRequest;
import com.project.web.model.AutocompleteResponse;
import com.project.web.service.IDatapoolService;

@Controller
public class AutocompleteRestController {

	@Autowired
	private IDatapoolService datapoolService;

	@GetMapping({ "/autocomplete", "/{language}/autocomplete" })
	public @ResponseBody AutocompleteResponse getAutocompleteResponse(@PathVariable(required = false, name = "language") String language, @RequestParam String query) {
		return datapoolService.getAutocompleteResponse(new AutocompleteRequest(query));
	}
}

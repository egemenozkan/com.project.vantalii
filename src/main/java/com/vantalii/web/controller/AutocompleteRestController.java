package com.vantalii.web.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.api.data.model.autocomplete.AutocompleteResponse;
import com.project.common.enums.Language;
import com.vantalii.web.model.AutocompleteRequest;
import com.vantalii.web.service.IDatapoolService;

@Controller
public class AutocompleteRestController {

	@Autowired
	private IDatapoolService datapoolService;

	@GetMapping({ "/autocomplete", "/places/autocomplete" })
	public @ResponseBody AutocompleteResponse getAutocompleteResponse(@RequestParam String query, @RequestParam String language) throws URISyntaxException {
		return datapoolService.getAutocompleteResponse(new AutocompleteRequest(query,Language.getByCode(language)));
	}
}

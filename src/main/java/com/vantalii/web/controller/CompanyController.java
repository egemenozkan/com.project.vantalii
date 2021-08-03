package com.vantalii.web.controller;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.api.data.model.place.PlaceRequest;
import com.project.common.enums.Language;
import com.project.common.utils.WebUtils;
import com.vantalii.web.service.impl.PlaceService;

@Controller
public class CompanyController {

	@Autowired
	private PlaceService placeService;

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@GetMapping({ "/about-vantalii", "/{language}/about-vantalii" })
	public String aboutUs(Model model, @PathVariable(required = false, name = "language") String language) {
		if (language == null) {
			language = Language.RUSSIAN.getCode();
		}
		PlaceRequest placeRequest = new PlaceRequest();
		placeRequest.setLanguage(Language.getByCode(language));
		placeRequest.setLimit(10);
		placeRequest.setRandom(Boolean.TRUE);
//		List<PlaceLandingPage> pages = placeService.getPlaceLandingPages(placeRequest);

		model.addAttribute("pages", Collections.emptyList());

		return "company/aboutVantalii";
	}

}

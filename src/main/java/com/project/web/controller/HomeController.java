package com.project.web.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.api.data.enums.Language;
import com.project.api.data.model.place.PlaceLandingPage;
import com.project.api.data.model.place.PlaceRequest;
import com.project.web.service.impl.PlaceService;
import com.project.web.utils.WebUtils;

public class HomeController {

	@Autowired
	private PlaceService placeService;

	final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private final Facebook facebook;

	@Inject
	public HomeController(Facebook facebook) {
		this.facebook = facebook;
	}

	@GetMapping({ "/", "/{language}/" })
	public String home(HttpServletRequest request, Model model, @PathVariable(required = false, name = "language") String language) {
		if (language == null) {
			language = Language.RUSSIAN.getCode();
		}
		PlaceRequest placeRequest = new PlaceRequest();
		placeRequest.setLanguage(Language.getByCode(language));
		placeRequest.setLimit(10);
		placeRequest.setRandom(Boolean.TRUE);
		List<PlaceLandingPage> pages = placeService.getPlaceLandingPages(placeRequest);

		model.addAttribute("pages", pages);

		List<Reference> friends = facebook.friendOperations().getFriends();
		model.addAttribute("friends", friends);

		return "index";
	}

	@GetMapping({ "/status" })
	public @ResponseBody String status(HttpServletRequest request, @PathVariable(required = false, name = "language") String language) {

		return String.format("requestHost: %s, language: %s", request.getHeader("host"), WebUtils.getCookieValue(request, "lang"));
	}
}

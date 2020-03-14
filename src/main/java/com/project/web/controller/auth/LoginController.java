package com.project.web.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.gson.Gson;
import com.project.web.service.IUserService;

@Controller
public class LoginController {
	@Autowired
	private Gson gson;
	@Autowired
	private IUserService userService;

	final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@GetMapping({ "/login", "/{language}/login" })
	public String getLogin(Model model, HttpServletResponse response, HttpServletRequest request) {
		return "login";
	}

}
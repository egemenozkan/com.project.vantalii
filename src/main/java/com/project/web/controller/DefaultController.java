package com.project.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {

//	@RequestMapping(value = "/")
//	public String home(@AuthenticationPrincipal  userDetail, Model model) {
////		model.addAttribute("email", userDetail.getUsername());
////		model.addAttribute("name", userDetail.getNickName());
//		return "home";
//	}

	@RequestMapping(value = "/error")
	public String error(@RequestParam String message, Model model) {
		model.addAttribute("message", message);
		return "error";
	}
}

package com.project.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.project.web.service.IUserService;

//@Controller
public class SignUpController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String redirectRequestToRegistrationPage(WebRequest request, ModelMap modelMap) {
//		Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
//		UserProfile userProfile = connection.fetchUserProfile();
//
//		UserCreateRequestVO userCreateRequestVO = UserCreateRequestVO.fromSocialUserProfile(userProfile);
//
//		modelMap.put("user", userCreateRequestVO);

		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String registrationUser(WebRequest request) throws Exception {
		try {
//			User user = userService.create(userCreateRequestVO);
//			providerSignInUtils.doPostSignUp(user.getEmail(), request);
//
//			FrontUserDetail frontUserDetail = new FrontUserDetail(user);
//
//			Authentication authentication = new UsernamePasswordAuthenticationToken(frontUserDetail, null, frontUserDetail.getAuthorities());
//			SecurityContextHolder.getContext().setAuthentication(authentication);

			return "redirect:/";
		} catch (Exception e) {
			return String.format("redirect:/error?message=%s", e.getMessage());
		}
	}
}

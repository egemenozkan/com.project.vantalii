package com.project.web.controller.auth;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.project.common.model.User;
import com.project.common.utils.WebUtils;
import com.project.web.service.IUserService;

@Controller
public class FacebookLoginController {
	@Autowired
	private Gson gson;
	@Autowired
	private IUserService userService;

	@Value("${facebook.app.id}")
	private String facebookAppId;
	@Value("${facebook.app.secret}")
	private String facebookAppSecret;

	final Logger logger = LoggerFactory.getLogger(FacebookLoginController.class);

	@SuppressWarnings("unchecked")
	@GetMapping({ "/login/facebook", "/{language}/login/facebook" })
	public String facebookLogin(Model model, HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> authDialogResponse = null;
		String code = request.getParameter("code");

		String redirectUrl = request.getRequestURL().toString().replace("http", "https");

		try {

			authDialogResponse = restTemplate.getForObject(
					generateFacebookGetAccessTokenUrl(facebookAppId, facebookAppSecret, redirectUrl, code), Map.class);
			if (logger.isInfoEnabled()) {
				logger.info("authDialogResponse response: {}", gson.toJson(authDialogResponse));
			}
			Map<String, Object> meResponse = new HashMap<>();
			if (authDialogResponse != null && authDialogResponse.get("access_token") != null) {
				String accessToken = authDialogResponse.get("access_token").toString();
				meResponse = restTemplate.getForObject(generateFacebookGetMeUrl(accessToken), Map.class);
				if (logger.isInfoEnabled()) {
					logger.info("meResponse response: {}", gson.toJson(meResponse));
				}
				logger.info("mePicture {}", generateFacebookGetPicture(meResponse.get("id").toString(), accessToken));
				byte[] imageBytes = restTemplate.getForObject(
						generateFacebookGetPicture(meResponse.get("id").toString(), accessToken), byte[].class);
				if (logger.isInfoEnabled()) {
					logger.info("mePicture {}, response: {}",
							generateFacebookGetPicture(meResponse.get("id").toString(), accessToken), imageBytes);
				}
			}

			User user = userService.findByUsernameOrEmail(WebUtils.getAsString(meResponse, "email"));

			if (user != null && !WebUtils.getAsString(meResponse, "id").equals(user.getFacebookId())) {
				user.setFacebookId(WebUtils.getAsString(meResponse, "id"));
				userService.updateSocialUserByEmail(user);
			} else if (user == null && WebUtils.getAsString(meResponse, "id") != null) {
				user = new User();
				user.setFacebookId(WebUtils.getAsString(meResponse, "id"));
				user.setFirstName(WebUtils.getAsString(meResponse, "first_name"));
				user.setLastName(WebUtils.getAsString(meResponse, "last_name"));
				user.setEmail(WebUtils.getAsString(meResponse, "email"));
				userService.registerUser(user);
			}

			if (user != null) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(),
						null, Arrays.asList(new SimpleGrantedAuthority("FACEBOOK_USER")));
				auth.setDetails(user);
				SecurityContextHolder.getContext().setAuthentication(auth);
				SecurityContext sc = SecurityContextHolder.getContext();
				sc.setAuthentication(auth);
				String referrer = request.getHeader("referer"); // Yes, with the legendary misspelling.
				logger.info("facebook login: referer: {}", referrer);

				response.sendRedirect(referrer == null ? "/" : referrer);

			}

		} catch (Exception e) {
			logger.error("facebook response: {}", gson.toJson(e));
		}
		return "login";
	}

	private String generateFacebookGetAccessTokenUrl(String appId, String appSecret, String redirectUrl, String code) {
		StringBuilder stringBuilder = new StringBuilder("https://graph.facebook.com/v3.2/oauth/access_token?");
		stringBuilder.append("client_id=");
		stringBuilder.append(appId);
		stringBuilder.append("&client_secret=");
		stringBuilder.append(appSecret);
		stringBuilder.append("&redirect_uri=");
		stringBuilder.append(redirectUrl);
		stringBuilder.append("&code=" + code);

		return stringBuilder.toString();
	}

	private String generateFacebookGetMeUrl(String accessToken) {
		StringBuilder stringBuilder = new StringBuilder("https://graph.facebook.com/me?");
		stringBuilder.append("access_token=");
		stringBuilder.append(accessToken);
		stringBuilder.append("&fields=id,email,first_name,last_name,age_range, picture");

		return stringBuilder.toString();
	}

	private String generateFacebookGetPicture(String userId, String accessToken) {
		StringBuilder stringBuilder = new StringBuilder("https://graph.facebook.com/").append(userId)
				.append("/picture?redirect=false&");
		stringBuilder.append("access_token=");
		stringBuilder.append(accessToken);

		return stringBuilder.toString();
	}

}
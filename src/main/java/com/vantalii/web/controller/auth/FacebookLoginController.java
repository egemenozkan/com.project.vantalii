package com.vantalii.web.controller.auth;

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
import com.vantalii.web.service.IUserService;

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
				logger.info("mePicture v11 {}", meResponse.get("picture"));
			}
			
			if (WebUtils.getAsString(meResponse, "email") == null) {
				throw new Exception("User e-postası yok");
			}

			User user = userService.findByUsernameOrEmail(WebUtils.getAsString(meResponse, "email"));

			if (user != null && (!WebUtils.getAsString(meResponse, "id").equals(user.getFacebookId()) || user.getId() > 0)){
				user.setFacebookId(WebUtils.getAsString(meResponse, "id"));
				userService.updateSocialUserByEmail(user);
			} else if (user == null && WebUtils.getAsString(meResponse, "id") != null) {
				user = new User();
				user.setFacebookId(WebUtils.getAsString(meResponse, "id"));
				user.setFirstName(WebUtils.getAsString(meResponse, "first_name"));
				user.setLastName(WebUtils.getAsString(meResponse, "last_name"));
				user.setEmail(WebUtils.getAsString(meResponse, "email"));
				long userId = userService.registerUser(user);
				//user.setId(userId);
			}

			if (user != null) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(),
						null, Arrays.asList(new SimpleGrantedAuthority("FACEBOOK_USER")));
				logger.info("facebook login: user: {}", gson.toJson(user));

				auth.setDetails(user);
				logger.info("facebook login: auth: {}",  gson.toJson(auth));

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
		StringBuilder stringBuilder = new StringBuilder("https://graph.facebook.com/v11.0/oauth/access_token?");
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
		StringBuilder stringBuilder = new StringBuilder("https://graph.facebook.com/v11.0/me?");
		stringBuilder.append("access_token=");
		stringBuilder.append(accessToken);
		stringBuilder.append("&fields=id,first_name,last_name,picture,email");
		stringBuilder.append("&auth_type=rerequest");
		stringBuilder.append("&scope=email,public_profile");

		return stringBuilder.toString();
	}

}
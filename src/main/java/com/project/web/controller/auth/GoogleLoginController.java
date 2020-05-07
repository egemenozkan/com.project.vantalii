package com.project.web.controller.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.project.common.model.User;
import com.project.common.utils.WebUtils;
import com.project.web.service.IUserService;

@Controller
public class GoogleLoginController {
	@Autowired
	private Gson gson;
	@Autowired
	private IUserService userService;

	private static final String GOOGLE_CLIENT_ID = "897437363481-oustapsredu1innp4ih4e82k77quq841.apps.googleusercontent.com";
	private static final String GOOGLE_CLIENT_SECRET = "zLZaTN9gC0hfG67o4sK0IMIH";

	/** OAuth 2.0 scopes. */
	private static final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile",
			"https://www.googleapis.com/auth/userinfo.email");

	final Logger logger = LoggerFactory.getLogger(GoogleLoginController.class);

	@GetMapping({ "/login/google", "/{language}/login/google" })
	public String googleLogin(Model model, HttpServletResponse response, HttpServletRequest request)
			throws GeneralSecurityException, IOException {
		HttpTransport transport = new ApacheHttpTransport();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList(GOOGLE_CLIENT_ID)).build();

		// (Receive idTokenString by HTTPS POST)

		GoogleIdToken idToken = verifier.verify(request.getParameter("idToken"));
		if (idToken != null) {
			Payload payload = idToken.getPayload();

			if (payload == null) {
				logger.error("payload is null");
				return "login";
			}

			User user = userService.findByUsernameOrEmail(payload.getEmail());
			if (user != null && payload.getSubject().equals(user.getGoogleId())) {
				user.setFirstName((String) payload.get("given_name"));
				user.setLastName((String) payload.get("family_name"));
				user.setPictureUrl((String) payload.get("picture"));
				userService.updateSocialUserByEmail(user);
			}

			if (user == null && payload.getSubject() != null) {
				user = new User();
				user.setGoogleId(payload.getSubject());
				user.setFirstName((String) payload.get("given_name"));
				user.setLastName((String) payload.get("family_name"));
				user.setPictureUrl((String) payload.get("picture"));
				user.setEmail(payload.getEmail());
				userService.registerUser(user);
				user = userService.findByUsernameOrEmail(payload.getEmail());

			}

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
					Arrays.asList(new SimpleGrantedAuthority("GOOGLE_USER")));
			auth.setDetails(user);
			SecurityContextHolder.getContext().setAuthentication(auth);
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(auth);
			response.sendRedirect(request.getHeader("referer"));

		} else {
			logger.error("::googleLogin Invalid ID token.");
		}
		return "login";
	}

	private String getSiteUrl(HttpServletRequest request) {
		StringBuilder strSiteUrl = new StringBuilder();
		strSiteUrl.append("https://");
		strSiteUrl.append(request.getHeader("host")).append("/");
		if (!request.getHeader("host").contains("vantalii.ru")) {
			strSiteUrl.append(WebUtils.getURILanguageCode(request.getRequestURI())).append("/");
		}
		
		return strSiteUrl.toString();
	}
}
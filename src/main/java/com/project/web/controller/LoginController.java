package com.project.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.gson.Gson;
import com.project.common.model.User;
import com.project.web.service.IUserService;
import com.project.web.utils.WebUtils;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;

@Controller
public class LoginController {
	@Autowired
	private Gson gson;
	@Autowired
	private IUserService userService;
	/*
	 * {"access_token":
	 * "EAAEFrISkVEYBAOIE7ilugDSqv0tCwH93ZBKx4XyiaZBNM9rItAMad8oTelygX6x9kvmraK4pC8zMxvkbwaDYtBZB85HO75EKgywwDSQX0je3LHmRvSg5XoNachBIkWDY3JueD9zOKOK1lIZCJ9OsGLYR4hgf2NVYjEep0AwXVAZDZD"
	 * ,"token_type":"bearer","expires_in":5178042}
	 * 
	 */
	@Value("${facebook.app.id}")
	private String facebookAppId;
	@Value("${facebook.app.secret}")
	private String facebookAppSecret;
	private static final String GOOGLE_CLIENT_ID = "670446652342-tetd8e4g5jtjmie01457ev3h8k6ft1o7.apps.googleusercontent.com";
	private static final String GOOGLE_CLIENT_SECRET = "TwqIOvHqG64PzheU8DA1_fcl";

	/** OAuth 2.0 scopes. */
	private static final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile",
			"https://www.googleapis.com/auth/userinfo.email");

	private static Oauth2 oauth2;

	final Logger logger = LoggerFactory.getLogger(LoginController.class);

	// custom login

	// @RequestMapping(value = "/login", method = RequestMethod.POST)
	// public void login(@RequestParam("username") final String username,
	// @RequestParam("password") final String password, final HttpServletRequest
	// request) {
	// UsernamePasswordAuthenticationToken authReq =
	// new UsernamePasswordAuthenticationToken(username, password);
	// Authentication auth = authManager.authenticate(authReq);
	// SecurityContext sc = SecurityContextHolder.getContext();
	// sc.setAuthentication(auth);
	// HttpSession session = request.getSession(true);
	// session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
	// }
	@GetMapping({ "/login", "/{language}/login" })
	public String getLogin(Model model, HttpServletResponse response, HttpServletRequest request) {

		return "login";
	}

	@SuppressWarnings("unchecked")
	@GetMapping({ "/login/facebook", "/{language}/login/facebook" })
	public String facebookLogin(Model model, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> authDialogResponse = null;
		String code = request.getParameter("code");
		// logger.info("fn referer: ",request.getParameter("referer"));
		// logger.info("fn referer: ",
		// URLEncoder.encode(request.getParameter("referer"), "UTF-8"));
		String redirectUrl = request.getRequestURL().toString().replace("http", "https");
		logger.info("redirect validaton: {}", redirectUrl.replace("http", "https"));
		try {
			// authDialogResponse = new HashMap<>();
			// authDialogResponse.put("access_token", "tetetest");
			authDialogResponse = restTemplate
					.getForObject(generateFacebookGetAccessTokenUrl(facebookAppId, facebookAppSecret, redirectUrl, code), Map.class);
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
				byte[] imageBytes = restTemplate.getForObject(generateFacebookGetPicture(meResponse.get("id").toString(), accessToken), byte[].class);
						if (logger.isInfoEnabled()) {
							logger.info("mePicture {}, response: {}", generateFacebookGetPicture(meResponse.get("id").toString(), accessToken), imageBytes);
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
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
						Arrays.asList(new SimpleGrantedAuthority("FACEBOOK_USER")));
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

	@GetMapping({ "/login/vkontakte", "/{language}/login/vkontakte" })
	public String vkontakteLogin(Model model, HttpServletResponse response, HttpServletRequest request) {
		String code = request.getParameter("code");

		try {
			TransportClient transportClient = HttpTransportClient.getInstance();
			VkApiClient vk = new VkApiClient(transportClient, new Gson(), 1);
			Integer vkontakteApiId = 6954043;
			String vkontakteClientSecret = "TZDBd5n6JAIiv6QnJ64a";
			String vkontakteServiceToken = "18d4fa9618d4fa9618d4fa961e18bee6ad118d418d4fa96446f93f9675540a697b7113b";
			String redirectUrl = "https://www.vantalii.com/login/vkontakte?referer=" + request.getParameter("referer");
			// String refererUrl = request.getHeader("referer");

			UserAuthResponse authResponse = vk.oauth().userAuthorizationCodeFlow(vkontakteApiId, vkontakteClientSecret, redirectUrl, code)
					.execute();
			logger.info("referer :{}, authResponse {}", request.getParameter("referer"), gson.toJson(authResponse));
			UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
			logger.info("actor {}", gson.toJson(actor));
			List<UserXtrCounters> getUsersResponse = vk.users().get(actor).userIds(authResponse.getUserId().toString()).execute();

			logger.info("profileInfo2 {}", gson.toJson(getUsersResponse.get(0)));

			User user = userService.findByUsernameOrEmail(authResponse.getEmail());
			if (user != null && authResponse.getUserId().toString().equals(user.getVkontakteId())) {
				user.setPictureUrl(getUsersResponse.get(0).getPhoto100());
				user.setFirstName(getUsersResponse.get(0).getFirstName());
				user.setLastName(getUsersResponse.get(0).getLastName());
				userService.updateSocialUserByEmail(user);
			} else if (user == null && authResponse.getUserId() > 0) {
				user = new User();
				user.setVkontakteId(authResponse.getUserId().toString());
				user.setFirstName(getUsersResponse.get(0).getFirstName());
				user.setLastName(getUsersResponse.get(0).getLastName());
				user.setEmail(authResponse.getEmail());
				user.setPictureUrl(getUsersResponse.get(0).getPhoto100());
				userService.registerUser(user);
			}

			if (user != null) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
						Arrays.asList(new SimpleGrantedAuthority("VKONTAKTE_USER")));
				auth.setDetails(user);
				SecurityContextHolder.getContext().setAuthentication(auth);
				SecurityContext sc = SecurityContextHolder.getContext();
				sc.setAuthentication(auth);
				response.sendRedirect(request.getParameter("referer"));
				// return "redirect:" + request.getParameter("referer");
			}

		} catch (Exception e) {
			logger.error("::vkontakte error {}", e);
		}

		return "login";
	}

	@GetMapping({ "/login/google", "/{language}/login/google" })
	public String googleLogin(Model model, HttpServletResponse response, HttpServletRequest request)
			throws GeneralSecurityException, IOException {
		HttpTransport transport = new ApacheHttpTransport();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				// Specify the CLIENT_ID of the app that accesses the backend:
				.setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
				// Or, if multiple clients access the backend:
				// .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
				.build();

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
	
	private String generateFacebookGetPicture(String userId,String accessToken) {
		StringBuilder stringBuilder = new StringBuilder("https://graph.facebook.com/").append(userId).append("/picture?redirect=false&");
		stringBuilder.append("access_token=");
		stringBuilder.append(accessToken);

		return stringBuilder.toString();
	}
	// https://oauth.vk.com/authorize?client_id=6954043&display=page&redirect_uri=https://www.vantalii.com/login/vkontakte&&response_type=code&v=5.95

	private String generateVkontakteGetCodeUrl(String clientId, String appSecret, String redirectUrl) {
		StringBuilder stringBuilder = new StringBuilder("https://oauth.vk.com/authorize?");
		stringBuilder.append("display=page&");
		stringBuilder.append("client_id=");
		stringBuilder.append(clientId);
		stringBuilder.append("&redirect_uri=");
		stringBuilder.append(redirectUrl);
		stringBuilder.append("&scope=friends,email,account");
		stringBuilder.append("&v=5.95");
		stringBuilder.append("&response_type=code");

		return stringBuilder.toString();
	}

}
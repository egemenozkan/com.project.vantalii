package com.project.web.controller.auth;

import java.util.Arrays;
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

import com.google.api.services.oauth2.Oauth2;
import com.google.gson.Gson;
import com.project.common.model.User;
import com.project.common.utils.WebUtils;
import com.project.web.service.IUserService;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;

@Controller
public class VkontakteLoginController {
	@Autowired
	private Gson gson;
	@Autowired
	private IUserService userService;

	private static Oauth2 oauth2;
	
	
	private final static int API_ID = 6954043;
	private final static String CLIENT_SECRET = "TZDBd5n6JAIiv6QnJ64a";
	private final static String SERVICE_TOKEN = "18d4fa9618d4fa9618d4fa961e18bee6ad118d418d4fa96446f93f9675540a697b7113b";

	final Logger logger = LoggerFactory.getLogger(VkontakteLoginController.class);

	@GetMapping({ "/login/vkontakte", "/{language}/login/vkontakte" })
	public String vkontakteLogin(Model model, HttpServletResponse response, HttpServletRequest request) {
		String code = request.getParameter("code");

		try {
			TransportClient transportClient = HttpTransportClient.getInstance();
			VkApiClient vk = new VkApiClient(transportClient, new Gson(), 1);
			String referrer = request.getParameter("referer") == null ? "" : getSiteUrl(request);
			String redirectUrl = request.getRequestURL().toString().replace("http", "https") +"?referer=" + referrer;
			UserAuthResponse authResponse = null;
			try {
				authResponse = vk.oauth()
						.userAuthorizationCodeFlow(API_ID, CLIENT_SECRET, redirectUrl, code).execute();
			} catch (Exception e) {
				logger.error("::userAuthorizationCodeFlow error {}, redirectUrl: {}, referrer: {}", e.getMessage(), redirectUrl, referrer);
			}

			logger.info("::userAuthorizationCodeFlow referer :{}, authResponse {}", request.getParameter("referer"),
					gson.toJson(authResponse));
			UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
			logger.info(":actor {}", gson.toJson(actor));
			List<UserXtrCounters> getUsersResponse = vk.users().get(actor).userIds(authResponse.getUserId().toString())
					.execute();

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
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(),
						null, Arrays.asList(new SimpleGrantedAuthority("VKONTAKTE_USER")));
				auth.setDetails(user);
				SecurityContextHolder.getContext().setAuthentication(auth);
				SecurityContext sc = SecurityContextHolder.getContext();
				sc.setAuthentication(auth);
				response.sendRedirect(request.getParameter("referer"));
			}

		} catch (Exception e) {
			logger.error("::vkontakte error {}", e);
		}

		return "login";
	}

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
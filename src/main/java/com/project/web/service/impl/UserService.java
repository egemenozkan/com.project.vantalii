package com.project.web.service.impl;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.common.model.User;
import com.project.web.service.IUserService;

@Service
public class UserService extends BaseApiService implements IUserService {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${security.api.auth-server-url}")
	private String authServerUrl;

	final Logger LOG = LoggerFactory.getLogger(PlaceService.class);

	@Override
	public User findByUsernameOrEmail(String usernameOrEmail) {
		StringBuilder strBuilder = new StringBuilder(authServerUrl)
				.append("/api/v1/users/email?email=").append(usernameOrEmail);
			
		
		return (User) this.getObject(strBuilder.toString(), User.class);
	}

	@Override
	public long registerUser(User user) {
		return (long) this.postObject(authServerUrl + "/api/v1/users/register", user, Long.class);
	}

	@Override
	public boolean findByFacebookId(String facebookId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsByEmailOrUsername(String usernameOrEmail) {
		URI endpoint = UriComponentsBuilder.fromUriString(authServerUrl + "/api/v1/users/available/{usernameOrEmail}/")
				.build(usernameOrEmail);
		return (boolean) this.getObject(endpoint.toString(), Boolean.class);
	}

	@Override
	public User updateSocialUserByEmail(User user) {
//		UriComponentsBuilder endpoint = UriComponentsBuilder.fromUriString(authServerUrl + "/api/v1/users");
		return (User) postObject(authServerUrl + "/api/v1/users/social", user, User.class);
	}

}

package com.project.web.service.impl;

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
		UriComponentsBuilder endpoint = UriComponentsBuilder.fromUriString(authServerUrl + "/api/v1/users/{usernameOrEmail}/");
		return (User) this.getObject(endpoint.toUriString(), User.class, usernameOrEmail);
	}

	@Override
	public long saveUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

}

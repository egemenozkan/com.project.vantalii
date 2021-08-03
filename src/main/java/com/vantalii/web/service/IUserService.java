package com.vantalii.web.service;

import com.project.common.model.User;

public interface IUserService {
	public User findByUsernameOrEmail(String usernameOrEmail);
	public boolean findByFacebookId(String facebookId);
	public boolean existsByEmailOrUsername(String emailOrUsername);
	public long registerUser(User user);
	public User updateSocialUserByEmail(User user);
}

package com.project.web.service;

import com.project.common.model.User;

public interface IUserService {
	public User findByUsernameOrEmail(String usernameOrEmail);

	long saveUser(User user);
}

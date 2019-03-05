package com.project.web.service;

import com.project.common.model.User;

public interface IUserService {
	  User findByUsername(final String username);
	  long saveUser(User user);
}

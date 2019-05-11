package com.cts.authenticate.services;

import com.cts.authenticate.domain.User;
import com.cts.authenticate.exception.UserAlreadyExistsException;
import com.cts.authenticate.exception.UserNotFoundException;

public interface UserService {

	boolean saveUser(User user) throws UserAlreadyExistsException;

	User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException;

}

package com.cts.authenticate.services;

import java.util.Map;

import com.cts.authenticate.domain.User;

public interface JwtSecurityTokenGenerator {
	
	Map<String, String> generateToken(User user);
}

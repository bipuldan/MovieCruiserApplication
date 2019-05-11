package com.cts.authenticate.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.authenticate.domain.User;
import com.cts.authenticate.services.JwtSecurityTokenGenerator;
import com.cts.authenticate.services.UserService;

@RestController
@RequestMapping("/api/v1/userservice")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtSecurityTokenGenerator tokenGenerator;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			userService.saveUser(user);
			return new ResponseEntity<String>("User registered successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("{ \" message\": \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User loginDetail) {
		try {
			if (null == loginDetail.getUserId() || null == loginDetail.getPassword()) {
				throw new Exception("User Id or Password canot be empty.");
			}
			User user = userService.findByUserIdAndPassword(loginDetail.getUserId(), loginDetail.getPassword());
			Map<String, String> map = tokenGenerator.generateToken(user);
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("{ \" message\": \"" + e.getMessage() + "\"}", HttpStatus.UNAUTHORIZED);
		}
	}
}


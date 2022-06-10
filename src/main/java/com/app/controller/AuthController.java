package com.app.controller;

import java.util.Collections;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.app.entity.UserEntity;
import com.app.models.LoginCredentials;
import com.app.repository.UserRepo;
import com.app.security.JWTFilter;
import com.app.security.JWTUtility;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
@Autowired UserRepo userRepo;
@Autowired JWTUtility jwtUtility;
@Autowired AuthenticationManager authenticationManager;
@Autowired PasswordEncoder passwordEncoder;
@PostMapping("/register")
public Map<String, Object> registerUser(@RequestBody UserEntity user){
	String encodedPass=passwordEncoder.encode(user.getPassword());
	user.setPassword(encodedPass);
	user=userRepo.save(user);
	String token=jwtUtility.generateToken(user.getUserName());
	return Collections.singletonMap("jwt-token", user);
	
}
@PostMapping("/login")
public Map<String,Object> loginUser(@RequestBody LoginCredentials loginCredentials) throws AuthenticationException{
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginCredentials.getUserName(), loginCredentials.getPassword());
		authenticationManager.authenticate(authenticationToken);
		String token=jwtUtility.generateToken(loginCredentials.getUserName());
		return Collections.singletonMap("jwt-token", token);
	
}
}

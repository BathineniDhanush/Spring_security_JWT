package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.UserEntity;
import com.app.repository.UserRepo;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserRepo userRepo;
	@GetMapping("/info")
	public UserEntity getUserDetails() {
		String email=(String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepo.findByUserName(email).get();
	}
}

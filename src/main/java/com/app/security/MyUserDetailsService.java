package com.app.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import com.app.entity.UserEntity;
import com.app.repository.UserRepo;
@Component
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserEntity> userRes= userRepo.findByUserName(username);
		if(userRes.isEmpty()) {
			throw new UsernameNotFoundException("Could not find the user with username: "+ username);
		}
		UserEntity user=userRes.get();
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
		
//		return null;
	}

}

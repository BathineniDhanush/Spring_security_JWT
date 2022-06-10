package com.app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JWTFilter extends OncePerRequestFilter {
	@Autowired
	JWTUtility jwtUtility;
	@Autowired
	MyUserDetailsService myUserDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authHeader=request.getHeader("Authorization");
		if(authHeader!=null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
			String jwt=authHeader.substring(7);
			if(jwt==null || jwt.isBlank()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid JWT Token in Bearer Header");
			}else {
				try {
					String userName=jwtUtility.validateTokenAndRetrieveSubject(jwt);
					UserDetails userDetails=myUserDetailsService.loadUserByUsername(userName);
					UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userName, userDetails.getPassword(),userDetails.getAuthorities());
					if(SecurityContextHolder.getContext().getAuthentication()==null) {
						SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					}
				}catch(JWTVerificationException exc) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid JWT Token");
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}

package com.app.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.repository.UserRepo;
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
@Autowired UserRepo userRepo;
@Autowired JWTFilter filter;
@Autowired MyUserDetailsService detailsService;
@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable().httpBasic().disable()
			.cors().and().authorizeHttpRequests()
			.antMatchers("/api/auth/**","/h2-console").permitAll()
			.antMatchers("/api/user/**").hasRole("USER")
			.and()
			.userDetailsService(detailsService)
			.exceptionHandling()
			.authenticationEntryPoint((request,response,authException)
			->
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
					)
			.and()
			.sessionManagement()
			
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
}
@Bean
public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}
@Bean
@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
}

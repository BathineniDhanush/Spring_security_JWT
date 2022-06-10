package com.app.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JWTUtility {
@Value("${jwt_secret}")
private String secret;
public String generateToken(String userName) throws IllegalArgumentException, JWTCreationException{
return JWT.create()
		.withSubject("User Details")
		.withClaim("userName", userName)
		.withIssuedAt(new Date())
		.withIssuer("Dhanush Bathineni")
		.sign(Algorithm.HMAC256(secret));
}
public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException{
	JWTVerifier verifier=JWT.require(Algorithm.HMAC256(secret))
						.withSubject("User Details")
						.withIssuer("Dhanush Bathineni")
						.build();
	DecodedJWT jwt=verifier.verify(token);
	return jwt.getClaim("userName").asString();
}
}

package com.flintzy.config;

import java.security.Key;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtKeyConfig {

	 @Bean
	     Key jwtSigningKey(JwtProperties props) {
	        return Keys.hmacShaKeyFor(props.getSecret().getBytes());
	    }
	}
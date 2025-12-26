package com.flintzy.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private final JwtProperties jwtProperties;
	private final Key signingKey;

	public JwtUtil(JwtProperties jwtProperties, Key signingKey) {
		this.jwtProperties = jwtProperties;
		this.signingKey = signingKey;
	}

	public String generateToken(String email) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + jwtProperties.getExpiration());

		return Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(expiry)
				.signWith(signingKey, SignatureAlgorithm.HS256).compact();
	}

	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}

	public boolean isTokenValid(String token) {
		try {
			extractAllClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
	}
}
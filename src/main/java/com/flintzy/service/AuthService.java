package com.flintzy.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.flintzy.config.JwtUtil;
import com.flintzy.entity.User;
import com.flintzy.repo.UserRepository;

@Service
public class AuthService {

	private final UserRepository repo;
	private final JwtUtil jwtUtil;

	public AuthService(UserRepository repo, JwtUtil jwtUtil) {
		this.repo = repo;
		this.jwtUtil = jwtUtil;
	}

	public Map<String, String> login(String email, String name) {
		User user = repo.findByEmail(email).orElseGet(() -> repo.save(new User(email, name)));

		String token = jwtUtil.generateToken(user.getEmail());

		return Map.of("token", token);
	}
}

package com.flintzy.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flintzy.service.FacebookService;

@RestController
@RequestMapping("/facebook")
public class FacebookController {

	private final FacebookService facebookService;

	public FacebookController(FacebookService facebookService) {
		this.facebookService = facebookService;
	}

	@PostMapping("/pages/link")
	public ResponseEntity<?> linkPages(@RequestBody Map<String, String> request, Authentication authentication) {

		String email = authentication.getName();
		String fbAccessToken = request.get("facebookAccessToken");

		if (fbAccessToken == null || fbAccessToken.isBlank()) {
			return ResponseEntity.badRequest().body("Facebook access token missing");
		}

		return ResponseEntity.ok(facebookService.linkPages(fbAccessToken, email));
	}

	@PostMapping("/pages/{pageId}/post")
	public ResponseEntity<?> publishPost(@PathVariable String pageId, @RequestBody Map<String, String> request) {

		return ResponseEntity.ok(facebookService.publishPost(pageId, request.get("message")));
	}
}

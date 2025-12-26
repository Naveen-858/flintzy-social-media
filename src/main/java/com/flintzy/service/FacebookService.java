package com.flintzy.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.flintzy.entity.FacebookPage;
import com.flintzy.entity.User;
import com.flintzy.repo.FacebookPageRepository;
import com.flintzy.repo.UserRepository;

@Service
@Transactional
public class FacebookService {

	private final FacebookPageRepository pageRepo;
	private final UserRepository userRepo;
	private final RestTemplate restTemplate;

	public FacebookService(FacebookPageRepository pageRepo, UserRepository userRepo) {
		this.pageRepo = pageRepo;
		this.userRepo = userRepo;
		this.restTemplate = new RestTemplate();
	}

	public String linkPages(String fbToken, String email) {

		User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		String url = "https://graph.facebook.com/v19.0/me/accounts?access_token=" + fbToken;

		Map<String, Object> response = restTemplate.getForObject(url, Map.class);

		if (response == null || !response.containsKey("data")) {
			throw new RuntimeException("Invalid Facebook response");
		}

		List<Map<String, Object>> pages = (List<Map<String, Object>>) response.get("data");

		if (pages.isEmpty()) {
			throw new RuntimeException(
					"No Facebook Pages found. Make sure the token has correct permissions and user manages pages.");
		}

		for (Map<String, Object> p : pages) {
			FacebookPage page = new FacebookPage();
			page.setPageId((String) p.get("id"));
			page.setPageName((String) p.get("name"));
			page.setPageAccessToken((String) p.get("access_token"));
			page.setUser(user);
			pageRepo.save(page);
		}

		return "Pages linked successfully";
	}

	public String publishPost(String pageId, String message) {

		FacebookPage page = pageRepo.findByPageId(pageId).orElseThrow(() -> new RuntimeException("Page not found"));

		Map<String, String> body = Map.of("message", message, "access_token", page.getPageAccessToken());

		restTemplate.postForObject("https://graph.facebook.com/v19.0/" + pageId + "/feed", body, String.class);

		return "Post published successfully";
	}
}

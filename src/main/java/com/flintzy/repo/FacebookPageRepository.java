package com.flintzy.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flintzy.entity.FacebookPage;

@Repository
public interface FacebookPageRepository extends JpaRepository<FacebookPage, Long> {
	Optional<FacebookPage> findByPageId(String pageId);

}

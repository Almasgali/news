package com.almasgali.news.repository;

import com.almasgali.news.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    List<User> findByLikedArticlesId(long articleId);
}

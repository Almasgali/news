package com.almasgali.news.repository;

import com.almasgali.news.data.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    List<Theme> findByArticlesId(long articleId);

    Optional<Theme> findByName(String name);
}

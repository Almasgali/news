package com.almasgali.news.repository;

import com.almasgali.news.data.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}

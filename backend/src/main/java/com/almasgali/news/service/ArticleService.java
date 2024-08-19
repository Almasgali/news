package com.almasgali.news.service;

import com.almasgali.news.data.model.Article;
import com.almasgali.news.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(@Autowired ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getLatestNews() {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        return articleRepository.findAllWithDateAfter(oneDayAgo);
    }
}

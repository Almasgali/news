package com.almasgali.news.controller;

import com.almasgali.news.data.model.Article;
import com.almasgali.news.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
@Validated
public class ArticleController {

        private final ArticleService articleService;

        public ArticleController(@Autowired ArticleService articleService) {
            this.articleService = articleService;
        }

        @GetMapping
        public List<Article> getLatestNews() {
            return articleService.getLatestNews();
        }
}

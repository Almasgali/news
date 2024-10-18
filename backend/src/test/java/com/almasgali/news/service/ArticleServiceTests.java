package com.almasgali.news.service;

import com.almasgali.news.data.dto.CommentRequest;
import com.almasgali.news.data.dto.CommentResponse;
import com.almasgali.news.data.dto.ThemeRequest;
import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Theme;
import com.almasgali.news.data.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


public class ArticleServiceTests extends CommonTests {

    @Autowired
    private ArticleService articleService;

    @Test
    @Transactional
    public void testInitialNews() {
        List<Article> articles = articleService.getLatestNews();
        Assertions.assertEquals(articles.size(),  4);
        List<String> titles = articles.stream().map(Article::getTitle).collect(Collectors.toList());
        Assertions.assertTrue(titles.contains("Как блогеру заработать в интернете"));
        Assertions.assertTrue(titles.contains("Выбираем тему для сайта"));
        Assertions.assertTrue(titles.contains("Лечение и обследования в Германии"));
        Assertions.assertTrue(titles.contains("Профессиональный свадебный фотограф"));
    }

    @Test
    @Transactional
    public void testLikeArticle() {
        Assertions.assertEquals(2, articleService.getLikedUsers(1).size());
        articleService.likeArticle(1, 3);
        Assertions.assertEquals(3, articleService.getLikedUsers(1).size());
        Assertions.assertTrue(articleService.getLikedUsers(1).stream().map(User::getId).collect(Collectors.toList()).contains(3L));
    }

    @Test
    @Transactional
    public void testCommentArticle() {
        Pageable p = PageRequest.of(0, 100);
        Assertions.assertEquals(4, articleService.getComments(1, p).getComments().size());
        articleService.commentArticle(1, 2, new CommentRequest("B"));
        List<CommentResponse> comments = articleService.getComments(1, p).getComments();
        Assertions.assertEquals(5, comments.size());
        Assertions.assertTrue(comments.stream().map(CommentResponse::getText).collect(Collectors.toList()).contains("B"));
    }

    @Test
    @Transactional
    public void testThemes() {
        List<Theme> oldThemes = articleService.getThemes();
        Assertions.assertEquals(3, oldThemes.size());
        Assertions.assertTrue(oldThemes.stream().map(Theme::getName).collect(Collectors.toSet()).contains("Фото"));
        Assertions.assertFalse(oldThemes.stream().map(Theme::getName).collect(Collectors.toSet()).contains("newTheme"));

        ThemeRequest newTheme = new ThemeRequest();
        newTheme.setName("newTheme");
        articleService.addTheme(newTheme);
        List<Theme> newThemes = articleService.getThemes();

        Assertions.assertEquals(4, newThemes.size());
        Assertions.assertTrue(newThemes.stream().map(Theme::getName).collect(Collectors.toSet()).contains("newTheme"));

        articleService.deleteTheme(1);
        Assertions.assertFalse(articleService.getThemes().stream().map(Theme::getName).collect(Collectors.toSet()).contains("Фото"));
    }

    @Test
    @Transactional
    public void testArticleThemes() {
        List<Theme> articleThemes = articleService.getArticleThemes(6);
        Assertions.assertEquals(1, articleThemes.size());
        Assertions.assertTrue(articleThemes.stream().map(Theme::getName).collect(Collectors.toSet()).contains("Фото"));
        Assertions.assertFalse(articleThemes.stream().map(Theme::getName).collect(Collectors.toSet()).contains("Заработок"));

        articleService.addThemeToArticle(6, 2);
        articleService.deleteThemeFromArticle(6, 1);

        List<Theme> newArticleThemes = articleService.getArticleThemes(6);
        Assertions.assertEquals(1, newArticleThemes.size());
        Assertions.assertTrue(newArticleThemes.stream().map(Theme::getName).collect(Collectors.toSet()).contains("Заработок"));
        Assertions.assertFalse(newArticleThemes.stream().map(Theme::getName).collect(Collectors.toSet()).contains("Фото"));
    }
}


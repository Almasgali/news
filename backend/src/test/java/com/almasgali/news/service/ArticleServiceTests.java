package com.almasgali.news.service;

import com.almasgali.news.data.dto.CommentRequest;
import com.almasgali.news.data.model.Comment;
import com.almasgali.news.data.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ArticleServiceTests {

    @LocalServerPort
    private Integer port;

    @Autowired
    private ArticleService articleService;

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
        List<Comment> comments = articleService.getComments(1, p).getComments();
        Assertions.assertEquals(5, comments.size());
        Assertions.assertTrue(comments.stream().map(Comment::getText).collect(Collectors.toList()).contains("B"));
    }
}


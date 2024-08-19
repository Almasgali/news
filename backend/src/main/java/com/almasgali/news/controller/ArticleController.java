package com.almasgali.news.controller;

import com.almasgali.news.data.dto.CommentRequest;
import com.almasgali.news.data.dto.CommentResponse;
import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Comment;
import com.almasgali.news.data.model.User;
import com.almasgali.news.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{id}/likes")
    public List<User> getLikedUsers(@PathVariable long id) {
        return articleService.getLikedUsers(id);
    }

    @PatchMapping("/{id}/likes")
    public void likeArticle(@PathVariable long id, @RequestParam long userId) {
        articleService.likeArticle(id, userId);
    }

    @GetMapping("/{id}/comments")
    public CommentResponse getComments(
            @PathVariable long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Pageable pageRequest = PageRequest.of(page, size);

        return articleService.getComments(id, pageRequest);
    }

    @PatchMapping("/{id}/comments")
    public void commentArticle(@PathVariable long id,
                               @RequestParam long userId,
                               @RequestBody CommentRequest commentRequest) {
        articleService.commentArticle(id, userId, commentRequest);
    }
}

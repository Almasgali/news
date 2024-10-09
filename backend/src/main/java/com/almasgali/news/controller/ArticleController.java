package com.almasgali.news.controller;

import com.almasgali.news.data.dto.ArticleRequest;
import com.almasgali.news.data.dto.ArticleThemesRequest;
import com.almasgali.news.data.dto.CommentRequest;
import com.almasgali.news.data.dto.CommentsResponse;
import com.almasgali.news.data.dto.ThemeRequest;
import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Theme;
import com.almasgali.news.data.model.User;
import com.almasgali.news.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        articleService.addArticle(articleRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        articleService.addArticle(articleRequest);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void editArticle(@Valid @RequestBody ArticleRequest articleRequest, @PathVariable long id) {
        articleService.editArticle(articleRequest, id);
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
    public CommentsResponse getComments(
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

    @DeleteMapping("/comments/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteComment(@PathVariable long id) {
        articleService.deleteComment(id);
    }
}

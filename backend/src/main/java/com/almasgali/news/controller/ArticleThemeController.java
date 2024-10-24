package com.almasgali.news.controller;

import com.almasgali.news.data.dto.ArticleThemesRequest;
import com.almasgali.news.data.dto.ThemeRequest;
import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Theme;
import com.almasgali.news.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@RestController
@RequestMapping("/news")
@Validated
public class ArticleThemeController {

    private final ArticleService articleService;

    public ArticleThemeController(@Autowired ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/themes")
    public List<Theme> getThemes() {
        return articleService.getThemes();
    }

    @PostMapping("/themes")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addTheme(@RequestBody ThemeRequest request) {
        articleService.addTheme(request);
    }

    @GetMapping("/{id}/themes")
    public List<Theme> getArticleThemes(@PathVariable long id) {
        return articleService.getArticleThemes(id);
    }

    @GetMapping("/themes/filter")
    public List<Article> filterArticlesByThemes(@RequestBody ArticleThemesRequest request) {
        return articleService.filterArticlesByThemes(request);
    }

    @DeleteMapping("/themes/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTheme(@PathVariable long id) {
        articleService.deleteTheme(id);
    }

    @PatchMapping("/{id}/themes")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addThemeToArticle(@PathVariable long id, @RequestParam long themeId) {
        articleService.addThemeToArticle(id, themeId);
    }

    @DeleteMapping("/{id}/themes")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteThemeFromArticle(@PathVariable long id, @RequestParam long themeId) {
        articleService.deleteThemeFromArticle(id, themeId);
    }
}

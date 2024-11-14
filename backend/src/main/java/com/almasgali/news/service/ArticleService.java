package com.almasgali.news.service;

import com.almasgali.news.data.dto.ArticleRequest;
import com.almasgali.news.data.dto.ArticleThemesRequest;
import com.almasgali.news.data.dto.CommentRequest;
import com.almasgali.news.data.dto.CommentResponse;
import com.almasgali.news.data.dto.CommentsResponse;
import com.almasgali.news.data.dto.ThemeRequest;
import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Comment;
import com.almasgali.news.data.model.Theme;
import com.almasgali.news.data.model.User;
import com.almasgali.news.repository.ArticleRepository;
import com.almasgali.news.repository.CommentRepository;
import com.almasgali.news.repository.ThemeRepository;
import com.almasgali.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ThemeRepository themeRepository;

    public ArticleService(@Autowired ArticleRepository articleRepository,
                          @Autowired UserRepository userRepository,
                          @Autowired CommentRepository commentRepository,
                          @Autowired ThemeRepository themeRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.themeRepository = themeRepository;
    }

    public List<Article> getLatestNews() {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        return articleRepository.findAllWithDateAfter(oneDayAgo);
    }

    public List<User> getLikedUsers(long articleId) {
        return userRepository.findByLikedArticlesId(articleId);
    }

    public void likeArticle(long articleId, long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Article article = articleRepository.findById(articleId).orElseThrow(NoSuchElementException::new);
        if (user.isArticleLiked(article)) {
            user.removeLikedArticle(article);
            article.removeLikedUser(user);
        } else {
            user.addLikedArticle(article);
            article.addLikedUser(user);
        }
        userRepository.save(user);
        articleRepository.save(article);
    }

    public void addThemeToArticle(long articleId, long themeId) {
        Theme theme = themeRepository.findById(themeId).orElseThrow(NoSuchElementException::new);
        Article article = articleRepository.findById(articleId).orElseThrow(NoSuchElementException::new);
        article.addTheme(theme);
        theme.addArticle(article);
        themeRepository.save(theme);
        articleRepository.save(article);
    }

    public void deleteThemeFromArticle(long articleId, long themeId) {
        Theme theme = themeRepository.findById(themeId).orElseThrow(NoSuchElementException::new);
        Article article = articleRepository.findById(articleId).orElseThrow(NoSuchElementException::new);
        article.deleteTheme(theme);
        theme.deleteArticle(article);
        themeRepository.save(theme);
        articleRepository.save(article);
    }

    public List<Theme> getArticleThemes(long id) {
        return themeRepository.findByArticlesId(id);
    }

    public void commentArticle(long articleId, long userId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Article article = articleRepository.findById(articleId).orElseThrow(NoSuchElementException::new);
        Comment comment = Comment.builder()
                .article(article)
                .user(user)
                .date(LocalDateTime.now())
                .text(commentRequest.getText())
                .build();
        commentRepository.save(comment);
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    public CommentsResponse getComments(long articleId, Pageable p) {
        Page<Comment> pComments = commentRepository.findAllByArticleIdOrderByDateDesc(articleId, p);
        List<Comment> comments = pComments.getContent();

        List<CommentResponse> responseComments = new ArrayList<>();
        for (Comment c : comments) {
            User author = c.getUser();
            responseComments.add(CommentResponse.builder()
                    .id(c.getId())
                    .text(c.getText())
                    .name(author.getName())
                    .surname(author.getSurname())
                    .date(c.getDate()).build());
        }

        return CommentsResponse.builder()
                .comments(responseComments)
                .currentPage(pComments.getNumber())
                .totalItems(pComments.getTotalElements())
                .totalPages(pComments.getTotalPages()).build();
    }

    public void editArticle(ArticleRequest request, long id) {
        articleRepository.updateArticle(
                id,
                request.getTitle(),
                request.getText(),
                request.getImage(),
                LocalDateTime.now());

        List<Theme> oldThemes = getArticleThemes(id);
        for (Theme th : oldThemes) {
            deleteThemeFromArticle(id, th.getId());
        }

        for (String th : request.getThemes()) {

            Theme theme = themeRepository.findByName(th)
                    .orElseThrow(NoSuchElementException::new);

            addThemeToArticle(id, theme.getId());
        }
    }

    public void deleteArticle(long id) {
        articleRepository.deleteById(id);
    }

    public void addArticle(ArticleRequest articleRequest) {
        Article article = articleRepository.save(Article.builder()
                .title(articleRequest.getTitle())
                .text(articleRequest.getText())
                .image(articleRequest.getImage())
                .date(LocalDateTime.now())
                .build());

        for (String th : articleRequest.getThemes()) {

            Theme theme = themeRepository.findByName(th)
                    .orElseThrow(NoSuchElementException::new);

            addThemeToArticle(article.getId(), theme.getId());
        }
    }

    public void addTheme(ThemeRequest request) {
        themeRepository.save(Theme.builder()
                .name(request.getName())
                .build());
    }

    public void deleteTheme(long id) {
        Theme theme = themeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        for (Article a : theme.getArticles()) {
            a.deleteTheme(theme);
            articleRepository.save(a);
        }
        themeRepository.deleteById(id);
    }

    public List<Theme> getThemes() {
        return themeRepository.findAll();
    }

    public List<Article> filterArticlesByThemes(ArticleThemesRequest request) {
        List<Article> response = new ArrayList<>();

        for (Article a : getLatestNews()) {
            Set<Long> articleThemesIds = a.getThemes().stream()
                    .map(Theme::getId)
                    .collect(Collectors.toSet());

            if (request.getForbiddenThemes() != null &&
                    !Collections.disjoint(articleThemesIds, request.getForbiddenThemes())) {
                continue;
            }

            response.add(a);
        }

        if (request.getFavouriteThemes() != null) {
            response.sort((a1, a2) -> {
                long count1 = a1.getThemes().stream()
                        .map(Theme::getId)
                        .filter(request.getFavouriteThemes()::contains)
                        .count();

                long count2 = a2.getThemes().stream()
                        .map(Theme::getId)
                        .filter(request.getFavouriteThemes()::contains)
                        .count();

                return Long.compare(count2, count1);
            });
        }

        return response;
    }
}

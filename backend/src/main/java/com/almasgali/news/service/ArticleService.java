package com.almasgali.news.service;

import com.almasgali.news.data.dto.ArticleRequest;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
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
        List<Article> latestNews = articleRepository.findAllWithDateAfter(oneDayAgo);
        latestNews.sort(Comparator.comparing(Article::getDate));
        return latestNews;
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
        Page<Comment> pComments = commentRepository.findByArticleId(articleId, p);
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
        responseComments.sort(Comparator.comparing(CommentResponse::getDate).reversed());
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
    }

    public void deleteArticle(long id) {
        articleRepository.deleteById(id);
    }

    public void addArticle(ArticleRequest articleRequest) {
        articleRepository.save(Article.builder()
                .title(articleRequest.getTitle())
                .text(articleRequest.getText())
                .image(articleRequest.getImage())
                .date(LocalDateTime.now())
                .build());
    }

    public void addTheme(ThemeRequest request) {
        themeRepository.save(Theme.builder()
                .name(request.getName())
                .build());
    }

    public void deleteTheme(long id) {
        themeRepository.deleteById(id);
    }

    public List<Theme> getThemes() {
        return themeRepository.findAll();
    }
}

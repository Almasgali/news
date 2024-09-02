package com.almasgali.news.service;

import com.almasgali.news.data.dto.CommentRequest;
import com.almasgali.news.data.dto.CommentResponse;
import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Comment;
import com.almasgali.news.data.model.User;
import com.almasgali.news.repository.ArticleRepository;
import com.almasgali.news.repository.CommentRepository;
import com.almasgali.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ArticleService(@Autowired ArticleRepository articleRepository,
                          @Autowired UserRepository userRepository,
                          @Autowired CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
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
        if (!user.isArticleLiked(article)) {
            user.addLikedArticle(article);
            article.addLikedUser(user);
        } else {
            user.removeLikedArticle(article);
            article.removeLikedUser(user);
        }
        userRepository.save(user);
        articleRepository.save(article);
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
        article.addComment(comment);
        articleRepository.save(article);
        user.addComment(comment);
        userRepository.save(user);
    }

    public CommentResponse getComments(long articleId, Pageable p) {
        Page<Comment> pComments = commentRepository.findByArticleId(articleId, p);
        List<Comment> comments = pComments.getContent();
        return CommentResponse.builder()
                .comments(comments)
                .currentPage(pComments.getNumber())
                .totalItems(pComments.getTotalElements())
                .totalPages(pComments.getTotalPages()).build();
    }
}

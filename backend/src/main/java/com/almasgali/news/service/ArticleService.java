package com.almasgali.news.service;

import com.almasgali.news.data.dto.CommentResponse;
import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Comment;
import com.almasgali.news.data.model.User;
import com.almasgali.news.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<User> getLikedUsers(long articleId) {
        return articleRepository.findByLikedUsersLikedArticlesId(articleId);
    }

    public CommentResponse getComments(long articleId, Pageable p) {
        Page<Comment> pComments = articleRepository.findByCommentsArticleId(articleId, p);
        List<Comment> comments = pComments.getContent();
        return CommentResponse.builder()
                .comments(comments)
                .currentPage(pComments.getNumber())
                .totalItems(pComments.getTotalElements())
                .totalPages(pComments.getTotalPages()).build();
    }
}

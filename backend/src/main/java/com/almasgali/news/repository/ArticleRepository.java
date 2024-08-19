package com.almasgali.news.repository;

import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Comment;
import com.almasgali.news.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE date >= :oneDayAgoDate ORDER BY date")
    List<Article> findAllWithDateAfter(@Param("oneDayAgoDate") LocalDateTime oneDayAgoDate);

    List<User> findByLikedUsersLikedArticlesId(long articleId);

    Page<Comment> findByCommentsArticleId(long articleId, Pageable p);
}

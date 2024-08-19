package com.almasgali.news.repository;

import com.almasgali.news.data.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE date >= :oneDayAgoDate ORDER BY date")
    List<Article> findAllWithDateAfter(@Param("oneDayAgoDate") LocalDateTime oneDayAgoDate);

//    @Query(
//            value = "UPDATE article SET balance = balance + ?2 WHERE id = ?1",
//            nativeQuery = true
//    )
//    @Modifying
//    int likeArticle(long id, @Min(0) long amount);
}

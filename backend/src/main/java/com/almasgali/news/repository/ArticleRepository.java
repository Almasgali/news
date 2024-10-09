package com.almasgali.news.repository;

import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE date >= :oneDayAgoDate ORDER BY date")
    List<Article> findAllWithDateAfter(@Param("oneDayAgoDate") LocalDateTime oneDayAgoDate);

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.title = ?2, a.text = ?3, a.image = ?4, a.date = ?5 WHERE a.id = ?1")
    void updateArticle(long id, String title, String text, String image, LocalDateTime date);

}

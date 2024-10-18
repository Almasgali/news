package com.almasgali.news.repository;

import com.almasgali.news.data.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByArticleIdOrderByDateDesc(long articleId, Pageable p);
}

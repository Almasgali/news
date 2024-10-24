package com.almasgali.news.data.loader;

import com.almasgali.news.data.dto.CommentRequest;
import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Comment;
import com.almasgali.news.data.model.User;
import com.almasgali.news.repository.ArticleRepository;
import com.almasgali.news.repository.CommentRepository;
import com.almasgali.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Component("commentLoader")
public class CommentLoader {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public CommentLoader(@Autowired UserRepository userRepository,
                         @Autowired ArticleRepository articleRepository,
                         @Autowired CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @EventListener(UsersLoadedEvent.class)
    public void load() {
        commentArticle(
                1,
                1,
                new CommentRequest("comment1"));
        commentArticle(
                1,
                1,
                new CommentRequest("comment1"));
        commentArticle(
                1,
                2,
                new CommentRequest("comment1"));
        commentArticle(
                1,
                3,
                new CommentRequest("comment1"));
        commentArticle(
                2,
                2,
                new CommentRequest("comment1"));
        commentArticle(
                2,
                3,
                new CommentRequest("comment1"));
    }

    private void commentArticle(long articleId, long userId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Article article = articleRepository.findById(articleId).orElseThrow(NoSuchElementException::new);
        Comment comment = Comment.builder()
                .article(article)
                .user(user)
                .date(LocalDateTime.now())
                .text(commentRequest.getText())
                .build();
        commentRepository.save(comment);
//        article.addComment(comment);
//        articleRepository.save(article);
//        user.addComment(comment);
//        userRepository.save(user);
    }
}

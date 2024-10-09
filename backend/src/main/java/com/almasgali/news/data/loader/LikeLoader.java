package com.almasgali.news.data.loader;

import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.User;
import com.almasgali.news.repository.ArticleRepository;
import com.almasgali.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component("likeLoader")
public class LikeLoader {


    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public LikeLoader(@Autowired UserRepository userRepository,
                      @Autowired ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @EventListener(UsersLoadedEvent.class)
    public void load() {
        likeArticle(2, 1);
        likeArticle(1, 1);
        likeArticle(3, 1);
        likeArticle(1, 2);
        likeArticle(5, 2);
        likeArticle(6, 3);
    }

    private void likeArticle(long articleId, long userId) {
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
}

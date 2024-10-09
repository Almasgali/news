package com.almasgali.news.data.loader;

import com.almasgali.news.data.model.Article;
import com.almasgali.news.data.model.Theme;
import com.almasgali.news.data.model.User;
import com.almasgali.news.repository.ArticleRepository;
import com.almasgali.news.repository.ThemeRepository;
import com.almasgali.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component("likeLoader")
public class LikesAndThemesLoader {


    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;
    private final ArticleRepository articleRepository;

    public LikesAndThemesLoader(@Autowired UserRepository userRepository,
                                @Autowired ThemeRepository themeRepository,
                                @Autowired ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
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

        themeRepository.save(Theme.builder()
                .name("Фото").build());
        themeRepository.save(Theme.builder()
                .name("Заработок").build());
        themeRepository.save(Theme.builder()
                .name("Блоггинг").build());

        addThemeToArticle(1, 2);
        addThemeToArticle(1, 3);
        addThemeToArticle(6, 1);
    }

    private void addThemeToArticle(long articleId, long themeId) {
        Theme theme = themeRepository.findById(themeId).orElseThrow(NoSuchElementException::new);
        Article article = articleRepository.findById(articleId).orElseThrow(NoSuchElementException::new);
        article.addTheme(theme);
        theme.addArticle(article);
        themeRepository.save(theme);
        articleRepository.save(article);
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

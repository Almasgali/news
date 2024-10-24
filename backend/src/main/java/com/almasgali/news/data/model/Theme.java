package com.almasgali.news.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "theme", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;
    @Getter
    private String name;
    @ManyToMany(mappedBy = "themes", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Article> articles;
    @ManyToMany(mappedBy = "favouriteThemes", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> favouriteForUsers;
    @ManyToMany(mappedBy = "forbiddenThemes", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> forbiddenForUsers;

    public void addForbForUser(User user) {
        initFavForUsers();
        forbiddenForUsers.add(user);
    }

    public void deleteForbForUser(User user) {
        initFavForUsers();
        forbiddenForUsers.remove(user);
    }

    private void initForbForUsers() {
        if (forbiddenForUsers == null) {
            forbiddenForUsers = new HashSet<>();
        }
    }

    public void addFavForUser(User user) {
        initFavForUsers();
        favouriteForUsers.add(user);
    }

    public void deleteFavForUser(User user) {
        initFavForUsers();
        favouriteForUsers.remove(user);
    }

    private void initFavForUsers() {
        if (favouriteForUsers == null) {
            favouriteForUsers = new HashSet<>();
        }
    }

    public void addArticle(Article article) {
        initArticles();
        articles.add(article);
    }

    public void deleteArticle(Article article) {
        initArticles();
        articles.remove(article);
    }

    private void initArticles() {
        if (articles == null) {
            articles = new HashSet<>();
        }
    }
}

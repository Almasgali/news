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

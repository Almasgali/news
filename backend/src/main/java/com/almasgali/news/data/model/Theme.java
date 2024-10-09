package com.almasgali.news.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "article_themes",
            joinColumns = @JoinColumn(name = "theme_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Article> articles;

    public void addArticle(Article article) {
        articles.add(article);
    }

    public void deleteArticle(Article article) {
        articles.remove(article);
    }
}

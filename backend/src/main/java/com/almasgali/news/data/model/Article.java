package com.almasgali.news.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "article", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq")
    private long id;
    private String title;
    private String text;
    private LocalDateTime date;
    @OneToMany(mappedBy = "article")
    private Set<Comment> comment;
    @ManyToMany(mappedBy = "likedArticles")
    private Set<User> likes;
}

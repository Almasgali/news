package com.almasgali.news.data.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "article", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;
    @Getter
    private String title;
    @Getter
    @Column(columnDefinition="TEXT")
    private String text;
    @Getter
    private LocalDateTime date;
    @Getter
    private String image;
    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Comment> comments;
    @ManyToMany(mappedBy = "likedArticles", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> likedUsers;

    public void addLikedUser(User user) {
        likedUsers.add(user);
    }

    public void removeLikedUser(User user) {
        likedUsers.remove(user);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}

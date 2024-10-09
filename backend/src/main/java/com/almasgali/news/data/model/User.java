package com.almasgali.news.data.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "user", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;
    @Getter
    private String name;
    @Getter
    private String surname;
    @Column(unique = true)
    private String email;
    @Getter
    private boolean isAdmin;
    @JsonIgnore
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Comment> comments;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "article_like",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Article> likedArticles;

    public boolean isArticleLiked(Article article) {
        return likedArticles.contains(article);
    }

    public void addLikedArticle(Article article) {
        likedArticles.add(article);
    }

    public void removeLikedArticle(Article article) {
        likedArticles.remove(article);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isAdmin) {
            return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
        }
        return Collections.emptyList();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}

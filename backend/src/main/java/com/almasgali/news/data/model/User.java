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

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
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
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Comment> comments;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_favourite_themes",
            inverseJoinColumns = @JoinColumn(name = "theme_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Theme> favouriteThemes;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_forbidden_themes",
            inverseJoinColumns = @JoinColumn(name = "theme_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Theme> forbiddenThemes;
    @ManyToMany(mappedBy = "likedUsers", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Article> likedArticles;

    public boolean isArticleLiked(Article article) {
        return likedArticles.contains(article);
    }

    public void addForbiddenTheme(Theme theme) {
        initForbThemes();
        forbiddenThemes.add(theme);
    }

    public void deleteForbiddenTheme(Theme theme) {
        initForbThemes();
        forbiddenThemes.remove(theme);
    }

    private void initForbThemes() {
        if (forbiddenThemes == null) {
            forbiddenThemes = new HashSet<>();
        }
    }

    public void addFavouriteTheme(Theme theme) {
        initFavThemes();
        favouriteThemes.add(theme);
    }

    public void deleteFavouriteTheme(Theme theme) {
        initFavThemes();
        favouriteThemes.remove(theme);
    }

    private void initFavThemes() {
        if (favouriteThemes == null) {
            favouriteThemes = new HashSet<>();
        }
    }

    public void addLikedArticle(Article article) {
        initArticles();
        likedArticles.add(article);
    }

    public void removeLikedArticle(Article article) {
        initArticles();
        likedArticles.remove(article);
    }

    private void initArticles() {
        if (likedArticles == null) {
            likedArticles = new HashSet<>();
        }
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

package com.almasgali.news.service;

import com.almasgali.news.data.model.Theme;
import com.almasgali.news.data.model.User;
import com.almasgali.news.repository.ThemeRepository;
import com.almasgali.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserThemeService {

    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;

    @Autowired
    public UserThemeService(UserRepository userRepository, ThemeRepository themeRepository) {
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
    }

    public List<Theme> getFavouriteThemes(long userId) {
        return themeRepository.findByFavouriteForUsersId(userId);
    }

    public List<Theme> getForbiddenThemes(long userId) {
        return themeRepository.findByForbiddenForUsersId(userId);
    }

    public void addFavouriteTheme(long userId, String themeName) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Theme theme = themeRepository.findByName(themeName).orElseThrow(NoSuchElementException::new);
        user.addFavouriteTheme(theme);
        theme.addFavForUser(user);
        userRepository.save(user);
        themeRepository.save(theme);
    }

    public void deleteFavouriteTheme(long userId, String themeName) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Theme theme = themeRepository.findByName(themeName).orElseThrow(NoSuchElementException::new);
        user.deleteFavouriteTheme(theme);
        theme.deleteFavForUser(user);
        userRepository.save(user);
        themeRepository.save(theme);
    }

    public void addForbiddenTheme(long userId, String themeName) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Theme theme = themeRepository.findByName(themeName).orElseThrow(NoSuchElementException::new);
        user.addForbiddenTheme(theme);
        theme.addForbForUser(user);
        userRepository.save(user);
        themeRepository.save(theme);
    }

    public void deleteForbiddenTheme(long userId, String themeName) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Theme theme = themeRepository.findByName(themeName).orElseThrow(NoSuchElementException::new);
        user.deleteForbiddenTheme(theme);
        theme.deleteForbForUser(user);
        userRepository.save(user);
        themeRepository.save(theme);
    }
}

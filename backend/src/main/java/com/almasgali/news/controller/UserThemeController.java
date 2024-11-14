package com.almasgali.news.controller;

import com.almasgali.news.data.model.Theme;
import com.almasgali.news.service.UserThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/themes")
public class UserThemeController {

    private final UserThemeService userThemeService;

    @Autowired
    public UserThemeController(UserThemeService userThemeService) {
        this.userThemeService = userThemeService;
    }

    @GetMapping("/favourite/{id}")
    public List<Theme> getFavouriteThemes(@PathVariable long id) {
        return userThemeService.getFavouriteThemes(id);
    }

    @GetMapping("/forbidden/{id}")
    public List<Theme> getForbiddenThemes(@PathVariable long id) {
        return userThemeService.getForbiddenThemes(id);
    }

    @PatchMapping("/favourite/{id}")
    public void addFavouriteTheme(@PathVariable long id, @RequestParam String themeName) {
        userThemeService.addFavouriteTheme(id, themeName);
    }

    @PatchMapping("/forbidden/{id}")
    public void addForbiddenTheme(@PathVariable long id, @RequestParam String themeName) {
        userThemeService.addForbiddenTheme(id, themeName);
    }

    @DeleteMapping("/favourite/{id}")
    public void deleteFavouriteTheme(@PathVariable long id, @RequestParam String themeName) {
        userThemeService.deleteFavouriteTheme(id, themeName);
    }

    @DeleteMapping("/forbidden/{id}")
    public void deleteForbiddenTheme(@PathVariable long id, @RequestParam String themeName) {
        userThemeService.deleteForbiddenTheme(id, themeName);
    }
}

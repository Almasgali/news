package com.almasgali.news.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ArticleThemesRequest {
    private List<Long> mandatoryThemes;
    private List<Long> forbiddenThemes;
}

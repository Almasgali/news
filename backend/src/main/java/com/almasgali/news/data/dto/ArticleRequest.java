package com.almasgali.news.data.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ArticleRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotBlank
    private String image;
}

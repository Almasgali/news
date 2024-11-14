package com.almasgali.news.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ThemeRequest {
    @NotBlank
    private String name;
}

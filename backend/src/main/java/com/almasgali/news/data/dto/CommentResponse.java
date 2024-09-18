package com.almasgali.news.data.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private String text;
    private String name;
    private String surname;
    private LocalDateTime date;
}

package com.almasgali.news.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentRequest {
    private String text;
}

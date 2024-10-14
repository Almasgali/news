package com.almasgali.news.data.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentsResponse {
    private List<CommentResponse> comments;
    private int currentPage;
    private long totalItems;
    private int totalPages;
}

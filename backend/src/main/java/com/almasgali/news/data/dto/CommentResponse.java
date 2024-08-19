package com.almasgali.news.data.dto;

import com.almasgali.news.data.model.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentResponse {
    private List<Comment> comments;
    private int currentPage;
    private long totalItems;
    private int totalPages;
}

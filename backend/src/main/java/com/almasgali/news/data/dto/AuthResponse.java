package com.almasgali.news.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private long id;
    private String name;
    private String surname;
    private String token;
    private String message;
}

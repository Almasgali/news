package com.almasgali.news.data.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
public class AuthRequest {
    private String email;
    @Size(min = 8, max = 32, message = "Password length should be in interval 8-128")
    private String password;
}

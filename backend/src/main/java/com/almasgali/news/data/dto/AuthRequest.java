package com.almasgali.news.data.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
public class AuthRequest {
    @Size(min = 4, max = 50, message = "Username length should be in interval 4-50")
    private String email;
    @Size(min = 8, max = 128, message = "Password length should be in interval 8-128")
    private String password;
}

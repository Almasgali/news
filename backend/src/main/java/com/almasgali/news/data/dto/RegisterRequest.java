package com.almasgali.news.data.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
public class RegisterRequest {
    private String name;
    private String surname;
    private String email;
    @Size(min = 8, max = 32, message = "Password length should be in interval 8-32")
    private String password;
}

package com.pipiolo.getinline.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @Email String email,
        @NotBlank String password
) {
    public static LoginRequest of(
            String email,
            String password
    ) {
        return new LoginRequest(email, password);
    }
}


package com.pipiolo.getinline.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record AdminRequest(
        @Email String email,
        @NotBlank String nickname,
        @NotBlank String password,
        @NotBlank String phoneNumber,
        String memo
) {
    public static AdminRequest of(
            String email,
            String nickname,
            String password,
            String phoneNumber,
            String memo
    ) {
        return new AdminRequest(email, nickname, password, phoneNumber, memo);
    }
}
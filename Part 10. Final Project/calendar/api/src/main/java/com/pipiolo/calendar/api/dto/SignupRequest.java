package com.pipiolo.calendar.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupRequest {

    private final String name;
    private final String email;
    private final String password;
    private final LocalDate birthday;
}

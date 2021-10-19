package com.pipiolo.getinline.controller.api;

import com.pipiolo.getinline.dto.AdminRequest;
import com.pipiolo.getinline.dto.LoginRequest;
import com.pipiolo.getinline.dto.APIDataResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class APIAuthController {

    @GetMapping("/sign-up")
    public APIDataResponse<Void> signUp(@Valid @RequestBody AdminRequest adminRequest) {
        return APIDataResponse.empty();
    }

    @GetMapping("/login")
    public APIDataResponse<Void> login(@Valid @RequestBody LoginRequest loginRequest) {
        return APIDataResponse.empty();
    }
}

package com.pipiolo.getinline.controller.api;

import com.pipiolo.getinline.dto.APIDataResponse;
import com.pipiolo.getinline.dto.AdminRequest;
import com.pipiolo.getinline.dto.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@RequestMapping("/api")
//@RestController
public class APIAuthController {

    @PostMapping("/sign-up")
    public APIDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
        return APIDataResponse.empty();
    }

    @PostMapping("/login")
    public APIDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return APIDataResponse.empty();
    }

}

package com.pipiolo.calendar.api.controller.api;

import com.pipiolo.calendar.api.dto.LoginRequest;
import com.pipiolo.calendar.api.dto.SignupRequest;
import com.pipiolo.calendar.api.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class LoginApiController {

    private LoginService loginService;

    @PostMapping("/api/sign-up")
    public ResponseEntity<Void> signup(
            @RequestBody SignupRequest request,
            HttpSession session
    ) {
        loginService.signup(request, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest request,
            HttpSession session
    ) {
        loginService.login(request, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Void> logout(
            HttpSession session
    ) {
        loginService.logout(session);
        return ResponseEntity.ok().build();
    }

}

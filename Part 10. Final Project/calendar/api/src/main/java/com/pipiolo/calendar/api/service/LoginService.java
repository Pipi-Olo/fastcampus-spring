package com.pipiolo.calendar.api.service;

import com.pipiolo.calendar.api.dto.user.LoginRequest;
import com.pipiolo.calendar.api.dto.user.SignupRequest;
import com.pipiolo.calendar.core.domain.entity.User;
import com.pipiolo.calendar.core.dto.UserCreateRequest;
import com.pipiolo.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    public static final String LOGIN_SESSION_KEY = "USER_ID";
    private final UserService userService;

    @Transactional
    public void signup(SignupRequest request, HttpSession session) {
        final User user = userService.create(new UserCreateRequest(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getBirthday()
        ));
        session.setAttribute(LOGIN_SESSION_KEY, user.getId());
    }

    @Transactional
    public void login(LoginRequest request, HttpSession session) {
        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
        if (userId != null) {
            return;
        }

        final Optional<User> user =
                userService.findPwMatchUser(request.getEmail(), request.getPassword());

        if (user.isPresent()) {
            session.setAttribute(LOGIN_SESSION_KEY, user.get().getId());
        } else {
            throw new RuntimeException("email or password not match.");
        }
    }

    public void logout(HttpSession session) {
        session.removeAttribute(LOGIN_SESSION_KEY);
    }
}

package com.pipiolo.calendar.core.service;

import com.pipiolo.calendar.core.domain.entity.User;
import com.pipiolo.calendar.core.domain.repository.UserRepository;
import com.pipiolo.calendar.core.dto.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("user already existed!");
                });

        return userRepository.save(new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getBirthday()
        ));
    }

    @Transactional
    public Optional<User> findPwMatchUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.getPassword().equals(password) ? user : null);
    }
}

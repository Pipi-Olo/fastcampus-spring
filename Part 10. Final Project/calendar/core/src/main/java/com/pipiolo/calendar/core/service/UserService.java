package com.pipiolo.calendar.core.service;

import com.pipiolo.calendar.core.domain.entity.User;
import com.pipiolo.calendar.core.domain.repository.UserRepository;
import com.pipiolo.calendar.core.dto.UserCreateRequest;
import com.pipiolo.calendar.core.util.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final Encryptor encryptor;
    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("user already existed!");
                });

        return userRepository.save(User.builder()
                .name(request.getName())
                .password(encryptor.encrypt(request.getPassword()))
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .build());
    }

    @Transactional
    public Optional<User> findPwMatchUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.isMatch(encryptor, password) ? user : null);
    }
}

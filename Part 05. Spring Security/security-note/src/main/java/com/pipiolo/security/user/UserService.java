package com.pipiolo.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(
            String username,
            String password
    ) {
        if (userRepository.findByUsername(username) != null) {
            throw new AlreadyRegisteredUserException();
        }

        return userRepository.save(new User(username, passwordEncoder.encode(password), "ROLE_USER"));
    }

    @Transactional
    public User signupAdmin(
            String username,
            String password
    ) {
        if (userRepository.findByUsername(username) != null) {
            throw new AlreadyRegisteredUserException();
        }

        return userRepository.save(new User(username, passwordEncoder.encode(password), "ROLE_ADMIN"));
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

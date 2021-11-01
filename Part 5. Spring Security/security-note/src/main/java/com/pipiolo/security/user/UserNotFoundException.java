package com.pipiolo.security.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException() {
        super("이미 등록된 유저입니다.");
    }
}

package com.pipiolo.security.user;

public class AlreadyRegisteredUserException extends RuntimeException {

    public AlreadyRegisteredUserException(String msg) {
        super(msg);
    }

    public AlreadyRegisteredUserException() {
        super("이미 등록된 유저입니다.");
    }
}

package com.pipiolo.dmaker.controller;

import com.pipiolo.dmaker.dto.DMakerErrorResponse;
import com.pipiolo.dmaker.exception.DMakerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class CMakerController {

    /**
     * refer handleException method in DMakerController class
     *
     * controller class 마다 handleException 생성
     * 1. 중복 코드 발생
     * 2. Error Handler 추가할 때 마다, All Controller 에 중복 코드 추가
     * -> global exceptionHandler class
     *
     */

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(DMakerException e,
                                               HttpServletRequest request) {
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDMakerErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMassage(e.getDetailMessage())
                .build();
    }
}

package com.pipiolo.dmaker.exception;

import com.pipiolo.dmaker.dto.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.pipiolo.dmaker.exception.DMakerErrorCode.INTERNAL_SERVER_ERROR;
import static com.pipiolo.dmaker.exception.DMakerErrorCode.INVALID_REQUEST;


/**
 * @RestControllerAdvice : RestController ErrorHandler Bean
 */

@Slf4j
@RestControllerAdvice
public class DMakerExceptionHandler {

    /**
     * Custom DMaker Error Handler
     * @ExceptionHandler(DMakerException.class) : DMakerException.class Error 발생 시, 호출
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

    /**
     *
     * @ExceptionHandler(value = {
     *             HttpRequestMethodNotSupportedException.class, : PostMapping, GetMapping Error
     *             MethodArgumentNotValidException.class         : NotNull 등 @valid Error
     *     })
     *
     * @Return : DMakerErrorResponse : Error Status 일관성을 위해 custom error message 로 변환 + import static
     */

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    public DMakerErrorResponse handleBadRequest(Exception e,
                                                HttpServletRequest request) {
        log.error("errorCode: {}, url: {}, message: {}",
                request.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(INVALID_REQUEST)
                .errorMassage(INVALID_REQUEST.getMessage())
                .build();
    }

    /**
     * The last Exception Handler
     * 개발자가 파악하기 힘든, 라이브러리 사용에서 발생하는 Errors
     *
     * @Return : DMakerErrorResponse : Error Status 일관성을 위해 custom error message 로 변환 + import static
     */

    @ExceptionHandler(value = Exception.class)
    public DMakerErrorResponse handleException(Exception e,
                                               HttpServletRequest request) {
        log.error("errorCode: {}, url: {}, message: {}",
                request.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMassage(INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}

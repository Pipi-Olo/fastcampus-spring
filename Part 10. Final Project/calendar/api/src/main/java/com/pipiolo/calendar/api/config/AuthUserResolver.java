package com.pipiolo.calendar.api.config;

import com.pipiolo.calendar.api.dto.AuthUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.pipiolo.calendar.api.service.LoginService.LOGIN_SESSION_KEY;

public class AuthUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        final Long userId = (Long) webRequest.getAttribute(LOGIN_SESSION_KEY, webRequest.SCOPE_SESSION);
        if (userId == null) {
            throw new RuntimeException("Bad Request. No Session.");
        }
        return AuthUser.of(userId);
    }
}

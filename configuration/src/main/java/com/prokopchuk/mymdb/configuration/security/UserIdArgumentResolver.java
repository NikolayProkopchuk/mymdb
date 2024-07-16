package com.prokopchuk.mymdb.configuration.security;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.prokopchuk.mymdb.common.adapter.web.annotation.AuthenticationUserId;
import com.prokopchuk.mymdb.configuration.security.model.SecurityUserDetails;

public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Objects.nonNull(parameter.getParameterAnnotation(AuthenticationUserId.class));
    }

    @Override
    public Long resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        SecurityUserDetails securityUserDetails = (SecurityUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return securityUserDetails.getId();
    }
}

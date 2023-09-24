package com.ssafy.enjoytrip.user.model.interceptor;

import com.ssafy.enjoytrip.global.error.JwtInvalidException;
import com.ssafy.enjoytrip.user.model.dto.NoAuth;
import com.ssafy.enjoytrip.user.model.service.JwtService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private static final String HEADER = "Authorization";

    private final JwtService jwtService;

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) {

        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (!checkAnnotation(handler, NoAuth.class)) {
            validateToken(request.getHeader(HEADER));
            request.setAttribute("userId", jwtService.parseToken(request.getHeader(HEADER)));
        }
        return true;

    }

    private void validateToken(final String token) {
        if (!jwtService.checkValidToken(token)) {
            throw new JwtInvalidException("로그인이 정상적으로 되지 않았습니다");
        }
    }

    private boolean checkAnnotation(final Object handler, final Class cls) {
        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        return handlerMethod.getMethodAnnotation(cls) != null;
    }
}

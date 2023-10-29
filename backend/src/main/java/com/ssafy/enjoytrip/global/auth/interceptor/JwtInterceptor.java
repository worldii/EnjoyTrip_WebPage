package com.ssafy.enjoytrip.global.auth.interceptor;


import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import com.ssafy.enjoytrip.global.auth.service.TokenService;
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

    public static final String OPTIONS = "OPTIONS";
    private static final String HEADER = "Authorization";

    private final TokenService tokenService;

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler) {

        if (OPTIONS.equals(request.getMethod())) {
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (!checkNoAuthClass((HandlerMethod) handler)) {
            final String token = request.getHeader(HEADER);
            tokenService.validateAccessToken(token);
            request.setAttribute("userId", tokenService.parseToken(token));
        }
        return true;
    }

    private boolean checkNoAuthClass(final HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(NoAuth.class) != null
                || handlerMethod.getBeanType().getAnnotation(NoAuth.class) != null;
    }
}

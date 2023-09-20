package com.ssafy.enjoytrip.user.model.interceptor;

import com.ssafy.enjoytrip.global.error.JwtInvalidException;
import com.ssafy.enjoytrip.user.model.dto.NoAuth;
import com.ssafy.enjoytrip.user.model.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private JwtService jwtService;
    private static final String HEADER_AUTH = "access-token";

    @Autowired
    public JwtInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private boolean checkAnnotation(Object handler, Class cls) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(cls) != null) { //해당 어노테이션이 존재하면 true.
            return true;
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // ToDo: cors preflight 방지 배포시 삭제
        String requestURI = request.getRequestURI();
        log.info("requestURI : {}", requestURI);

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return true;
        }
        boolean check = checkAnnotation(handler, NoAuth.class);
        if (check) return true;
        final String token = request.getHeader(HEADER_AUTH);
        log.info("JWT Target Token - {} ", token);

        if (token != null && jwtService.checkValidToken(token)) {
            log.info("JWT Access Token Valid");
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new JwtInvalidException("로그인이 정상적으로 되지 않았습니다");
        }
    }
}

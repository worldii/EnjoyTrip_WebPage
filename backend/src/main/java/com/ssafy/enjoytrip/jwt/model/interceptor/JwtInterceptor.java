package com.ssafy.enjoytrip.jwt.model.interceptor;

import com.ssafy.enjoytrip.jwt.model.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // ToDo: cors preflight 방지 배포시 삭제
        if("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return true;
        }

        final String token = request.getHeader(HEADER_AUTH);
        log.info("JWT Target Token - {} ",token);


        if(token != null && jwtService.checkValidToken(token)){
            log.info("JWT Access Token Valid");
            return true;
        }

        log.info("JWT Access Token InValid");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}

package com.ssafy.enjoytrip.global.auth.controller;

import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import com.ssafy.enjoytrip.global.auth.service.AuthService;
import com.ssafy.enjoytrip.user.model.entity.User;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 로그인, 로그아웃, refreshToken 갱신
    @NoAuth
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
        @RequestBody final User user, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;

        String token = request.getHeader("refresh-token");
        if (authService.checkValidToken(token)) {
            if (authService.canRefresh(token, user.getUserId())) {
                String accessToken = authService.generateAccessToken(user.getUserId());
                resultMap.put("access-token", accessToken);
                status = HttpStatus.ACCEPTED;
            } else {
                status = HttpStatus.UNAUTHORIZED;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}

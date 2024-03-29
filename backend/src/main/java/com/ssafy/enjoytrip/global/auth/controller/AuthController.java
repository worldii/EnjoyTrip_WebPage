package com.ssafy.enjoytrip.global.auth.controller;


import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import com.ssafy.enjoytrip.global.auth.model.dto.request.AccessTokenRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.AccessTokenResponse;
import com.ssafy.enjoytrip.global.auth.model.dto.response.RefreshTokenResponse;
import com.ssafy.enjoytrip.global.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> generateRefreshToken(
            @LoginUser final String userId) {
        return ResponseEntity.ok(tokenService.generateRefreshToken(userId));
    }

    @NoAuth
    @PostMapping("/access-token")
    public ResponseEntity<AccessTokenResponse> generateAccessToken(
            @RequestBody final AccessTokenRequest request) {
        return ResponseEntity.ok(
                tokenService.generateAccessToken(request.getUserId(), request.getRefreshToken()));
    }
}

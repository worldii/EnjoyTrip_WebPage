package com.ssafy.enjoytrip.user.controller;


import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import com.ssafy.enjoytrip.global.auth.model.dto.request.LogoutRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;
import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserModifyRequest;
import com.ssafy.enjoytrip.user.model.dto.response.UserResponse;
import com.ssafy.enjoytrip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @NoAuth
    @PostMapping
    public ResponseEntity<Boolean> join(@RequestBody final UserAddRequest requestUser) {
        return ResponseEntity.ok(userService.join(requestUser));
    }

    @NoAuth
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody final UserLoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }


    @GetMapping("/info/{userId}")
    public ResponseEntity<UserResponse> getInfo(@PathVariable final String userId) {
        return ResponseEntity.ok(userService.getInformation(userId));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
        @LoginUser final String loginUser,
        @RequestBody final LogoutRequest request
    ) {
        userService.logout(loginUser, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> modify(
        @RequestBody final UserModifyRequest request,
        @LoginUser final String userId
    ) {
        userService.modify(request, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(
        @PathVariable final String userId,
        @LoginUser final String loginUser
    ) {
        userService.delete(userId, loginUser);
        return ResponseEntity.noContent().build();
    }
}

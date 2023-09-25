package com.ssafy.enjoytrip.user.controller;


import com.ssafy.enjoytrip.user.model.dto.LoginUser;
import com.ssafy.enjoytrip.user.model.dto.NoAuth;
import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserModifyRequest;
import com.ssafy.enjoytrip.user.model.dto.response.TokenResponse;
import com.ssafy.enjoytrip.user.model.dto.response.UserResponse;
import com.ssafy.enjoytrip.user.model.entity.User;
import com.ssafy.enjoytrip.user.model.service.JwtService;
import com.ssafy.enjoytrip.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final JwtService jwtService;

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


    @NoAuth
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody User user, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;

        String token = request.getHeader("refresh-token");
        if (jwtService.checkValidToken(token)) {
            if (jwtService.canRefresh(token, user.getUserId())) {
                String accessToken = jwtService.generateAccessToken(user.getUserId());
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

    @GetMapping("/{userId}")
    public ResponseEntity<?> logout(@PathVariable String userId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();

        HttpStatus status = HttpStatus.ACCEPTED;

        try {
            jwtService.deleteRefreshToken(userId);
            resultMap.put("success", true);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            resultMap.put("success", false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultMap, status);
    }
}

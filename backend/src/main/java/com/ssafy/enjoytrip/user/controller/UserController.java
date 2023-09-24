package com.ssafy.enjoytrip.user.controller;


import com.ssafy.enjoytrip.user.model.dto.NoAuth;
import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.user.model.dto.response.TokenResponse;
import com.ssafy.enjoytrip.user.model.entity.User;
import com.ssafy.enjoytrip.user.model.service.JwtService;
import com.ssafy.enjoytrip.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    @PutMapping
    public ResponseEntity<?> modify(@RequestBody User requestUser) {
        Map<String, Object> resultMap = new HashMap<>();

        int result = userService.modify(requestUser);

        if (result > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }

        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
    }

    @NoAuth
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> leave(@PathVariable String userId) {
        Map<String, Object> resultMap = new HashMap<>();

        int result = userService.leave(userId);
        jwtService.deleteToken(userId);

        if (result > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }

        return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
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
            jwtService.deleteToken(userId);
            resultMap.put("success", true);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            resultMap.put("success", false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<?> getInfo(@PathVariable String userId) {
        Map<String, Object> resultMap = new HashMap<>();

        HttpStatus status = HttpStatus.ACCEPTED;

        User user = userService.getInformation(userId);

        if (Objects.nonNull(user)) {
            resultMap.put("success", true);
            resultMap.put("userInfo", user);
        } else {
            resultMap.put("success", false);
        }

        return new ResponseEntity<>(resultMap, status);
    }
}

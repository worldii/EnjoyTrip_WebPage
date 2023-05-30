package com.ssafy.enjoytrip.user.api;


import com.ssafy.enjoytrip.jwt.model.service.JwtService;
import com.ssafy.enjoytrip.user.model.dto.User;
import com.ssafy.enjoytrip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://127.0.0.1:8080","http://192.168.0.1:8080","http://localhost:8080","http://localhost:8081"})
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User requestUser){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            User loginUser = userService.login(requestUser.getUserId(),requestUser.getPassword());
            if (loginUser != null) {
                String accessToken = jwtService.generateAccessToken(requestUser.getUserId());
                String refreshToken = jwtService.generateRefreshToken(requestUser.getUserId());

                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);
                resultMap.put("success", true);
            } else {
                resultMap.put("success", false);
            }
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            resultMap.put("success",false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @PostMapping
    public ResponseEntity<?> join(@RequestBody User requestUser){
        Map<String, Object> resultMap = new HashMap<>();

        int result = userService.join(requestUser);

        if(result>0){
            resultMap.put("success",true);
        }else{
            resultMap.put("success",false);
        }

        return new ResponseEntity<>(resultMap,HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<?> modify(@RequestBody User requestUser){
        Map<String, Object> resultMap = new HashMap<>();

        int result = userService.modify(requestUser);

        if(result>0){
            resultMap.put("success",true);
        }else{
            resultMap.put("success",false);
        }

        return new ResponseEntity<>(resultMap,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> leave(@PathVariable String userId){
        Map<String, Object> resultMap = new HashMap<>();

        int result = userService.leave(userId);
        jwtService.deleteToken(userId);

        if(result>0){
            resultMap.put("success",true);
        }else{
            resultMap.put("success",false);
        }

        return new ResponseEntity<>(resultMap,HttpStatus.ACCEPTED);
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody User user, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;

        String token = request.getHeader("refresh-token");

        if (jwtService.checkValidToken(token)) {
            if (jwtService.canRefresh(token,user.getUserId())) {

                String accessToken = jwtService.generateAccessToken(user.getUserId());

                resultMap.put("access-token", accessToken);
                status = HttpStatus.ACCEPTED;
            }else{
                status = HttpStatus.UNAUTHORIZED;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> logout(@PathVariable String userId,HttpServletRequest request){
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
    public ResponseEntity<?> getInfo(@PathVariable String userId){
        Map<String, Object> resultMap = new HashMap<>();

        HttpStatus status = HttpStatus.ACCEPTED;

        User user = userService.getInformation(userId);

        if(Objects.nonNull(user)){
            resultMap.put("success",true);
            resultMap.put("userInfo",user);
        }else{
            resultMap.put("success",false);
        }

        return new ResponseEntity<>(resultMap, status);
    }
}

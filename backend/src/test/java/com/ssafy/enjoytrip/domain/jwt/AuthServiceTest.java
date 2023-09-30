package com.ssafy.enjoytrip.domain.jwt;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ssafy.enjoytrip.global.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    AuthService authService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void testLoadBean() {
        //given - when - then
        assertNotNull(authService);
    }

    @Test
    void testGenerateRefreshToken() {
        //given
        String userId = "test";

        //when
        String refreshToken = authService.generateRefreshToken(userId);

        //then
        assertEquals(refreshToken, redisTemplate.opsForValue().get(userId));
        assertEquals(authService.checkValidToken(refreshToken), true);
    }

    @Test
    void testGenerateAccessToken() {
        //given
        String userId = "test";

        //when
        String refreshToken = authService.generateAccessToken(userId);

        //then
        assertEquals(authService.checkValidToken(refreshToken), true);
    }

    @Test
    void testCanRefresh() {
        //given
        String correctUserId = "junyoung";
        String wrongUserId = "jongha";
        String refreshToken = authService.generateRefreshToken(correctUserId);

        //when
        boolean canRefresh = authService.canRefresh(refreshToken, correctUserId);
        boolean canNotRefresh = authService.canRefresh(refreshToken, wrongUserId);

        //then
        assertEquals(canRefresh, true);
        assertEquals(canNotRefresh, false);
    }
}

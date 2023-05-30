package com.ssafy.enjoytrip.jwt;


import com.ssafy.enjoytrip.jwt.model.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {
    @Autowired
    JwtService jwtService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void testLoadBean(){
        //given - when - then
        assertNotNull(jwtService);
    }

    @Test
    void testGenerateRefreshToken(){
        //given
        String userId = "test";

        //when
        String refreshToken = jwtService.generateRefreshToken(userId);

        //then
        assertEquals(refreshToken, redisTemplate.opsForValue().get(userId));
        assertEquals(jwtService.checkValidToken(refreshToken), true);
    }

    @Test
    void testGenerateAccessToken(){
        //given
        String userId = "test";

        //when
        String refreshToken = jwtService.generateAccessToken(userId);

        //then
        assertEquals(jwtService.checkValidToken(refreshToken), true);
    }

    @Test
    void testCanRefresh(){
        //given
        String correctUserId = "junyoung";
        String wrongUserId = "jongha";
        String refreshToken = jwtService.generateRefreshToken(correctUserId);

        //when
        boolean canRefresh = jwtService.canRefresh(refreshToken,correctUserId);
        boolean canNotRefresh = jwtService.canRefresh(refreshToken,wrongUserId);

        //then
        assertEquals(canRefresh, true);
        assertEquals(canNotRefresh, false);
    }
}

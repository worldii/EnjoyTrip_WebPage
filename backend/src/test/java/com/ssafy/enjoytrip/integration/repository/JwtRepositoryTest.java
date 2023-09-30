package com.ssafy.enjoytrip.integration.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ssafy.enjoytrip.global.auth.dao.JwtRepository;
import com.ssafy.enjoytrip.global.auth.model.entity.RefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@DisplayName("JwtRepository 통합 테스트")
@SpringBootTest
class JwtRepositoryTest {

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName("JwtRepository Bean 주입 테스트")
    void testLoadBean() {
        assertNotNull(jwtRepository);
    }

    @Test
    @DisplayName("RefreshToken 저장 테스트")
    void 리프레시_토른_정상적으로_저장_테스트() {
        //given
        RefreshToken refreshToken = createRefreshTokenDto();

        //when
        jwtRepository.save(refreshToken);

        //then
        assertNotNull(redisTemplate.opsForValue().get(refreshToken.getUserId()));
        assertEquals(
            refreshToken.getRefreshToken(),
            redisTemplate.opsForValue().get(refreshToken.getUserId()
            )
        );
    }

    @Test
    @DisplayName("RefreshToken 조회 테스트")
    void 리프레시_토큰_정상적으로_조회_테스트() {
        //given
        RefreshToken refreshToken = createRefreshTokenDto();
        jwtRepository.save(refreshToken);
        String wrongUserId = "wrongUserId";

        //when
        RefreshToken existTokenDto = jwtRepository
            .findByUserId(refreshToken.getUserId())
            .orElse(null);
        RefreshToken notExistTokenDto = jwtRepository.findByUserId(wrongUserId)
            .orElse(null);

        //then
        assertAll(
            () -> assertEquals(refreshToken.getRefreshToken(), existTokenDto.getRefreshToken()),
            () -> assertEquals(refreshToken.getUserId(), existTokenDto.getUserId()),
            () -> assertEquals(null, notExistTokenDto)
        );
    }

    @Test
    @DisplayName("RefreshToken 삭제 테스트")
    void 리프레시_토큰_정상적으로_삭제_테스트() {
        //given
        RefreshToken refreshToken = createRefreshTokenDto();
        jwtRepository.save(refreshToken);

        //when
        jwtRepository.deleteRefreshToken(refreshToken.getUserId());

        //then
        assertEquals(null, redisTemplate.opsForValue().get(refreshToken.getUserId()));
    }


    private RefreshToken createRefreshTokenDto() {
        String refreshToken = "TestToken";
        String userId = "test";
        return RefreshToken
            .builder()
            .refreshToken(refreshToken)
            .userId(userId)
            .build();
    }
}

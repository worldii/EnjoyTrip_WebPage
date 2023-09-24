package com.ssafy.enjoytrip.domain.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ssafy.enjoytrip.user.model.dao.JwtRepository;
import com.ssafy.enjoytrip.user.model.dto.RefreshToken;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class JwtRepositoryTest {

    @Autowired
    JwtRepository jwtRepository;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void testLoadBean() {
        System.out.println(jwtRepository);
        assertNotNull(jwtRepository);
    }

    @Test
    void testSaveRefreshToken() {
        System.out.println("Optional.empty() = " + Optional.empty().get());
        //given
        RefreshToken refreshToken = createRefreshTokenDto();

        //when
        jwtRepository.save(refreshToken);

        //then
        assertNotNull(redisTemplate.opsForValue().get(refreshToken.getUserId()));
        assertEquals(refreshToken.getRefreshToken(),
            redisTemplate.opsForValue().get(refreshToken.getUserId()));
    }

    @Test
    void testFind() {
        //given
        RefreshToken refreshToken = createRefreshTokenDto();
        jwtRepository.save(refreshToken);

        //when
        RefreshToken existTokenDto = jwtRepository
            .findByUserId(refreshToken.getUserId())
            .orElse(null);

        RefreshToken notExistTokenDto = jwtRepository.findByUserId("").orElse(null);

        //then
        assertEquals(refreshToken.getRefreshToken(), existTokenDto.getRefreshToken());
        assertEquals(refreshToken.getUserId(), existTokenDto.getUserId());
        assertEquals(notExistTokenDto, null);
    }

    @Test
    void testDelete() {
        //given
        RefreshToken refreshToken = createRefreshTokenDto();
        jwtRepository.save(refreshToken);

        //when
        jwtRepository.deleteRefreshToken(refreshToken.getUserId());

        //then
        assertEquals(redisTemplate.opsForValue().get(refreshToken.getUserId()), null);
    }


    RefreshToken createRefreshTokenDto() {
        String refreshToken = "TestToken";
        String userId = "test";

        RefreshToken refreshTokenDto = RefreshToken
            .builder()
            .refreshToken(refreshToken)
            .userId(userId)
            .build();

        return refreshTokenDto;
    }
}

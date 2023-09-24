package com.ssafy.enjoytrip.domain.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ssafy.enjoytrip.user.model.dao.JwtRepository;
import com.ssafy.enjoytrip.user.model.dto.RefreshTokenDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class JwtRepositoryTest {

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
        RefreshTokenDto refreshTokenDto = createRefreshTokenDto();

        //when
        jwtRepository.save(refreshTokenDto);

        //then
        assertNotNull(redisTemplate.opsForValue().get(refreshTokenDto.getUserId()));
        assertEquals(refreshTokenDto.getRefreshToken(),
            redisTemplate.opsForValue().get(refreshTokenDto.getUserId()));
    }

    @Test
    void testFind() {
        //given
        RefreshTokenDto refreshTokenDto = createRefreshTokenDto();
        jwtRepository.save(refreshTokenDto);

        //when
        RefreshTokenDto existTokenDto = jwtRepository.findByUserId(refreshTokenDto.getUserId());

        RefreshTokenDto notExistTokenDto = jwtRepository.findByUserId("");

        //then
        assertEquals(refreshTokenDto.getRefreshToken(), existTokenDto.getRefreshToken());
        assertEquals(refreshTokenDto.getUserId(), existTokenDto.getUserId());
        assertEquals(notExistTokenDto, null);
    }

    @Test
    void testDelete() {
        //given
        RefreshTokenDto refreshTokenDto = createRefreshTokenDto();
        jwtRepository.save(refreshTokenDto);

        //when
        jwtRepository.delete(refreshTokenDto.getUserId());

        //then
        assertEquals(redisTemplate.opsForValue().get(refreshTokenDto.getUserId()), null);
    }


    RefreshTokenDto createRefreshTokenDto() {
        String refreshToken = "TestToken";
        String userId = "test";

        RefreshTokenDto refreshTokenDto = RefreshTokenDto
            .builder()
            .refreshToken(refreshToken)
            .userId(userId)
            .build();

        return refreshTokenDto;
    }
}

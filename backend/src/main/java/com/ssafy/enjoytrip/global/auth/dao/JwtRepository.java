package com.ssafy.enjoytrip.global.auth.dao;

import com.ssafy.enjoytrip.global.auth.model.entity.RefreshToken;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class JwtRepository {

    private final Long timeOutSecond;
    private final RedisTemplate<String, String> redisTemplate;

    public JwtRepository(
        final RedisTemplate<String, String> redisTemplate,
        @Value("${refreshtoken.timeout.second}") final long TIME_OUT_SECOND
    ) {
        this.redisTemplate = redisTemplate;
        this.timeOutSecond = TIME_OUT_SECOND;
    }

    public void save(final RefreshToken refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(
            refreshToken.getUserId(),
            refreshToken.getRefreshToken(),
            timeOutSecond,
            TimeUnit.SECONDS
        );
    }

    public Optional<RefreshToken> findByUserId(final String userId) {
        final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        final String refreshToken = valueOperations.get(userId);
        if (Objects.isNull(refreshToken)) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(refreshToken, userId));
    }

    public void deleteRefreshToken(final String userId) {
        final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.getAndDelete(userId);
    }
}

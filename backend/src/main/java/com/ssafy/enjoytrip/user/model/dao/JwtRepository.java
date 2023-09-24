package com.ssafy.enjoytrip.user.model.dao;

import com.ssafy.enjoytrip.user.model.dto.RefreshToken;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class JwtRepository {

    @Value("${refreshtoken.timeout.second}")
    private long TIME_OUT_SECOND;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public JwtRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(final RefreshToken refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(
            refreshToken.getUserId(),
            refreshToken.getRefreshToken(),
            TIME_OUT_SECOND,
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

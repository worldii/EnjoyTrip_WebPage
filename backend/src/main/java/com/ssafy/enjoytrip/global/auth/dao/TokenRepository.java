package com.ssafy.enjoytrip.global.auth.dao;


import com.ssafy.enjoytrip.global.auth.model.entity.RefreshToken;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TokenRepository {

    private final Long timeOutDay;
    private final RedisTemplate<String, String> redisTemplate;

    public TokenRepository(
            final RedisTemplate<String, String> redisTemplate,
            @Value("${refreshtoken.timeout.days}") final Long TIME_OUT_DAY) {
        this.redisTemplate = redisTemplate;
        this.timeOutDay = TIME_OUT_DAY;
    }

    public void save(final RefreshToken refreshToken) {
        final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.set(
                refreshToken.getUserId(), refreshToken.getTokenName(), timeOutDay, TimeUnit.DAYS);
    }

    public void registerBlackList(final String accessToken, final Long timeOutSecond) {
        final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(accessToken, "logout", timeOutSecond, TimeUnit.SECONDS);
    }

    public Optional<RefreshToken> findRefreshTokenByUserId(final String userId) {
        final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        final String refreshToken = valueOperations.get(userId);
        if (Objects.isNull(refreshToken)) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(refreshToken, userId));
    }

    public void delete(final String userId) {
        final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.getAndDelete(userId);
    }
}

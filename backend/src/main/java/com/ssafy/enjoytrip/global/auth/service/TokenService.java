package com.ssafy.enjoytrip.global.auth.service;

import com.ssafy.enjoytrip.global.auth.dao.TokenRepository;
import com.ssafy.enjoytrip.global.auth.model.dto.AccessTokenResponse;
import com.ssafy.enjoytrip.global.auth.model.dto.RefreshTokenResponse;
import com.ssafy.enjoytrip.global.auth.model.entity.RefreshToken;
import com.ssafy.enjoytrip.global.error.UserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TokenService {

    private static final String USER_ACCESS_TOKEN_KEY = "userId";
    private final String secretKey;
    private final Long accessExpiredMinutes;
    private final TokenRepository tokenRepository;

    public TokenService(
        @Value("${jwt.salt}") final String secretKey,
        @Value("${jwt.access-token-expmin}") final Long accessExpiredMinutes,
        final TokenRepository tokenRepository
    ) {
        this.secretKey = secretKey;
        this.accessExpiredMinutes = accessExpiredMinutes;
        this.tokenRepository = tokenRepository;
    }

    public AccessTokenResponse generateAccessToken(final String userId) {
        final RefreshToken refreshToken = tokenRepository.findRefreshTokenByUserId(userId)
            .orElseThrow(() -> new UserException("존재하지 않는 유저입니다."));

        final Claims claims = Jwts.claims()
            .setSubject("accessToken")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + accessExpiredMinutes));

        claims.put(USER_ACCESS_TOKEN_KEY, refreshToken.getUserId());

        final String accessToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
            .compact();

        return new AccessTokenResponse(accessToken);
    }

    @Transactional
    public void registerBlackList(final String accessToken) {
        final Date expiration = Jwts.parser()
            .setSigningKey(secretKey.getBytes())
            .parseClaimsJws(accessToken)
            .getBody().getExpiration();

        tokenRepository.registerBlackList(accessToken, expiration.getTime());
    }

    public RefreshTokenResponse generateRefreshToken(final String userId) {

        final RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), userId);
        tokenRepository.save(refreshToken);

        return RefreshTokenResponse.from(refreshToken);
    }

    public void validateAccessToken(final String accessToken) {
        if (accessToken == null) {
            throw new UserException("토큰이 유효하지 않습니다.");
        }
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(accessToken);
        } catch (Exception e) {
            throw new UserException("토큰이 유효하지 않습니다.");
        }
    }

    @Transactional
    public void deleteRefreshToken(final String userId) {
        tokenRepository.delete(userId);
    }

    public String parseToken(final String token) {
        validateAccessToken(token);
        return Jwts.parser()
            .setSigningKey(secretKey.getBytes())
            .parseClaimsJws(token)
            .getBody()
            .get(USER_ACCESS_TOKEN_KEY, String.class);
    }
}

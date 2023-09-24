package com.ssafy.enjoytrip.user.model.service;

import com.ssafy.enjoytrip.user.model.dao.JwtRepository;
import com.ssafy.enjoytrip.user.model.dto.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final String salt;
    private final Long accessExpireMin;
    private final Long refreshExpireMin;
    private final JwtRepository jwtRepository;

    public JwtService(
        @Value("${jwt.salt}") final String salt,
        @Value("${jwt.access-token-expmin}") final Long accessExpireMin,
        @Value("${jwt.refresh-token-expmin}") final Long refreshExpireMin,
        final JwtRepository jwtRepository
    ) {
        this.salt = salt;
        this.accessExpireMin = accessExpireMin;
        this.refreshExpireMin = refreshExpireMin;
        this.jwtRepository = jwtRepository;
    }

    public String generateAccessToken(final String userId) {
        return generateToken(
            "userId",
            userId,
            "access-token",
            accessExpireMin
        );
    }

    private <T> String generateToken(
        final String key, final T data,
        final String subject, final Long expireMin
    ) {
        final Claims claims = Jwts.claims()
            .setSubject(subject)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expireMin));

        claims.put(key, data);

        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, salt.getBytes())
            .compact();
    }

    public String generateRefreshToken(final String userId) {
        final String refreshToken = generateToken(
            "userId", userId, "refresh-token", refreshExpireMin);
        jwtRepository.save(new RefreshToken(refreshToken, userId));
        return refreshToken;
    }


    public boolean canRefresh(String refreshToken, String userId) {
        RefreshToken refreshTokenDto = jwtRepository
            .findByUserId(userId)
            .orElse(null);

        return !Objects.isNull(refreshTokenDto) &&
            refreshTokenDto.getRefreshToken().equals(refreshToken);
    }


    public boolean checkValidToken(final String token) {
        try {
            Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void deleteRefreshToken(final String userId) {
        jwtRepository.deleteRefreshToken(userId);
    }
}

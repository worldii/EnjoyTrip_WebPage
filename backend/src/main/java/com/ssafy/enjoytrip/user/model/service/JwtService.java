package com.ssafy.enjoytrip.user.model.service;

import com.ssafy.enjoytrip.user.model.dao.JwtRepository;
import com.ssafy.enjoytrip.user.model.dto.RefreshTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtService {

    private final String SALT;
    private final Long EXPIRE_MIN;
    private final JwtRepository jwtRepository;

    public JwtService(
        @Value("${jwt.salt}") final String SALT,
        @Value("${jwt.expmin}") final Long EXPIRE_MIN,
        final JwtRepository jwtRepository
    ) {
        this.SALT = SALT;
        this.EXPIRE_MIN = EXPIRE_MIN;
        this.jwtRepository = jwtRepository;
    }

    public String generateRefreshToken(final String userId) {
        String refreshToken = generateToken("userId", userId, "refresh-token",
            2 * 7 * 24 * 60 * 60 * 1000);
        jwtRepository.save(new RefreshTokenDto(refreshToken, userId));

        return refreshToken;
    }

    public String generateAccessToken(String userId) {
        return generateToken("userId", userId, "access-token", 60 * 1000 * 1 * EXPIRE_MIN);
    }

    public boolean canRefresh(String refreshToken, String userId) {
        RefreshTokenDto refreshTokenDto = jwtRepository.findByUserId(userId);

        if (!Objects.isNull(refreshTokenDto) && refreshTokenDto.getRefreshToken()
            .equals(refreshToken)) {
            return true;
        }

        return false;
    }

    private <T> String generateToken(String key, T data, String subject, long expireMin) {
        Claims claims = Jwts.claims()
            .setSubject(subject)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expireMin));

        claims.put(key, data);

        String jwt = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS256, SALT.getBytes())
            .compact();

        log.info("JWT Generate Token - {}", jwt);
        return jwt;
    }

    public boolean checkValidToken(final String token) {
        log.info("JWT Check Valid Token - {}", token);
        try {
            Jwts.parser().setSigningKey(SALT.getBytes()).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void deleteToken(final String userId) {
        jwtRepository.delete(userId);
    }
}

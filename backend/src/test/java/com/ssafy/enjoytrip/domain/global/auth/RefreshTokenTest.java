package com.ssafy.enjoytrip.domain.global.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.global.auth.model.entity.RefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RefreshToken 테스트")
class RefreshTokenTest {

    @Test
    @DisplayName("RefreshToken 생성 테스트")
    void createRefreshToken() {
        assertThatCode(
            () ->
                RefreshToken.builder()
                    .tokenName("test")
                    .userId("test")
                    .build()
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("RefreshToken 생성 실패 테스트 : 토큰 이름 오류")
    void createRefreshTokenFailTokenName() {
        assertThatCode(
            () ->
                RefreshToken.builder()
                    .userId("test")
                    .build()
        )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("토큰이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("RefreshToken 생성 실패 테스트 : 사용자 아이디 오류")
    void createRefreshTokenFailUserId() {
        assertThatCode(
            () ->
                RefreshToken.builder()
                    .tokenName("test")
                    .build()
        )
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("사용자 아이디가 존재하지 않습니다.");
    }
}

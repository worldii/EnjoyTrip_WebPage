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
}

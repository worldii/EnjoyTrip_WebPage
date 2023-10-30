package com.ssafy.enjoytrip.global.auth.model.entity;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshToken {

    private final String tokenName;
    private final String userId;

    public RefreshToken(final String tokenName, final String userId) {
        validateTokenName(tokenName);
        validateUserId(userId);

        this.tokenName = tokenName;
        this.userId = userId;
    }

    private void validateTokenName(final String tokenName) {
        if (tokenName == null || tokenName.isEmpty()) {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
    }

    private void validateUserId(final String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("사용자 아이디가 존재하지 않습니다.");
        }
    }
}

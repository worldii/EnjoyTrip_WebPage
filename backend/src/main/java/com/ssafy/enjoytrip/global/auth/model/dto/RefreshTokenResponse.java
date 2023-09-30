package com.ssafy.enjoytrip.global.auth.model.dto;

import com.ssafy.enjoytrip.global.auth.model.entity.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {

    private String tokenName;

    public static RefreshTokenResponse from(final RefreshToken refreshToken) {
        return new RefreshTokenResponse(
            refreshToken.getTokenName()
        );
    }
}

package com.ssafy.enjoytrip.jwt.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;



@Getter
@Builder
@RequiredArgsConstructor
public class RefreshTokenDto {
    private final String refreshToken;
    private final String userId;
}

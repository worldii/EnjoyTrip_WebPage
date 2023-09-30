package com.ssafy.enjoytrip.global.auth.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Builder
@RequiredArgsConstructor
public class RefreshToken {

    private final String refreshToken;
    private final String userId;
}

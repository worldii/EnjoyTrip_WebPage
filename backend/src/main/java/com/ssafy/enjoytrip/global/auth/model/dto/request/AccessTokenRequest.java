package com.ssafy.enjoytrip.global.auth.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenRequest {

    private String refreshToken;
    private String userId;
}

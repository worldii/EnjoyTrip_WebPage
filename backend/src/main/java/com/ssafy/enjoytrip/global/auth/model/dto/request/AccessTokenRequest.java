package com.ssafy.enjoytrip.global.auth.model.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenRequest {

    @NotBlank(message = "리프레시 토큰은 필수 입력 값입니다.")
    private String refreshToken;
    @NotBlank(message = "유저 아이디는 필수 입력 값입니다.")
    private String userId;
}

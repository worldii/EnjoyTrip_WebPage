package com.ssafy.enjoytrip.global.auth.model.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoutRequest {

    @NotBlank(message = "유저 액세스 토큰은 필수 입력 값입니다.")
    private String accessToken;
}

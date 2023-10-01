package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.core.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.core.user.service.UserService;
import com.ssafy.enjoytrip.global.auth.dao.TokenRepository;
import com.ssafy.enjoytrip.global.auth.model.dto.request.LogoutRequest;
import com.ssafy.enjoytrip.global.auth.model.dto.response.AccessTokenResponse;
import com.ssafy.enjoytrip.global.auth.model.dto.response.RefreshTokenResponse;
import com.ssafy.enjoytrip.global.auth.model.dto.response.TokenResponse;
import com.ssafy.enjoytrip.global.auth.service.TokenService;
import com.ssafy.enjoytrip.global.error.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("Token Service 통합 테스트")
@SpringBootTest
@Sql({"/truncate.sql", "/user.sql"})
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setUp() {
        redisTemplate.delete(redisTemplate.keys("*"));
    }

    @Test
    @DisplayName("accessToken을 정상 저장한다.")
    void saveAccessTokenTest() {
        // given
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .build();
        userService.join(userAddRequest);

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("test")
            .build();
        userService.login(userLoginRequest);

        // when
        AccessTokenResponse accessToken = tokenService.generateAccessToken("jongha");

        // then
        assertThat(accessToken)
            .extracting("tokenName")
            .isNotNull();
    }

    // TODO : 이거 롤백 왜 안되는 거임? 트랜잭션 설정이 안되어있나?
    @Test
    @DisplayName("accessToken은 유저가 없을 때 저장하지 않는다.")
    void saveAccessTokenTestFailWithNoLoginUser() {
        // given
        String wrongUser = "wrongUser";

        // when & then
        assertThatCode(() -> tokenService.generateAccessToken(wrongUser))
            .isInstanceOf(UserException.class)
            .hasMessage("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("accessToken을 정상적으로 검증한다.")
    void validateAccessTokenTest() {
        // given
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .build();
        userService.join(userAddRequest);
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("test")
            .build();
        TokenResponse loginToken = userService.login(userLoginRequest);

        // when & then
        assertThatCode(() -> tokenService.validateAccessToken(loginToken.getAccessToken()))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("accessToken이 null or Empty 이면 검증에 실패한다.")
    void validateAccessTokenTestFailWithExpiredToken(final String wrongAccessToken) {
        // when & then
        assertThatCode(() -> tokenService.validateAccessToken(wrongAccessToken))
            .isInstanceOf(UserException.class)
            .hasMessage("토큰이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("accessToken이 만료되면 검증에 실패한다.")
    void validateAccessTokenTestFailWithExpiredToken() {
        // given
        String expiredAccessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3NUb2tlbiIsImlhdCI6MTY5NjA3NzA3MCwiZXhwIjoxNjk2MDc3MDcwLCJ1c2VySWQiOiJqb25naGEifQ.vQ584QrR8PedldokCyfzzuX82AJ-2nCqWPwc90SOFBM";

        // when & then
        assertThatCode(() -> tokenService.validateAccessToken(expiredAccessToken))
            .isInstanceOf(UserException.class)
            .hasMessage("토큰이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("token을 정상적으로 파싱한다")
    void parseTokenTestWithSuccess() {
        // given
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .build();

        userService.join(userAddRequest);

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("test")
            .build();
        TokenResponse loginToken = userService.login(userLoginRequest);

        // when
        String userId = tokenService.parseToken(loginToken.getAccessToken());

        // then
        assertThat(userId).isEqualTo(userLoginRequest.getUserId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("token을 파싱할 때 유효하지 않은 토큰이면 예외를 발생한다")
    void parseTokenFailWithNotValidToken(final String wrongToken) {
        // when & then
        assertThatCode(() -> tokenService.parseToken(wrongToken))
            .isInstanceOf(UserException.class)
            .hasMessage("토큰이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("refreshToken을 정상 저장한다.")
    void saveRefreshTokenTest() {
        // given
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .build();
        userService.join(userAddRequest);

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("test")
            .build();
        userService.login(userLoginRequest);

        // when
        RefreshTokenResponse refreshToken = tokenService.generateRefreshToken("jongha");

        // then
        assertThat(refreshToken)
            .extracting("tokenName")
            .isNotNull();
    }


    @Test
    @DisplayName("refreshToken을 정상적으로 삭제한다")
    void deleteRefreshToken() {
        // given
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .build();

        userService.join(userAddRequest);

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("test")
            .build();
        userService.login(userLoginRequest);

        // when
        tokenService.deleteRefreshToken(userLoginRequest.getUserId());

        // then
        assertThat(tokenRepository.findRefreshTokenByUserId(userLoginRequest.getUserId()))
            .isEmpty();
    }

    @Test
    @DisplayName("유저가 로그아웃 하면 blackList에 정상적으로 액세스 토큰을 등록한다.")
    void registerBlackListTest() {
        // given
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .build();
        userService.join(userAddRequest);

        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("test")
            .build();
        TokenResponse loginToken = userService.login(userLoginRequest);

        // when
        userService.logout(
            userLoginRequest.getUserId(),
            LogoutRequest.builder()
                .accessToken(loginToken.getAccessToken())
                .build()
        );

        // then
        assertThat(redisTemplate.opsForValue().get(loginToken.getAccessToken()))
            .isNotNull();
    }
}

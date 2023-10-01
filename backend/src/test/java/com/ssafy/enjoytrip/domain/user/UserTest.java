package com.ssafy.enjoytrip.domain.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.core.user.model.entity.User;
import com.ssafy.enjoytrip.global.error.UserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("User 도메인 테스트")
class UserTest {

    @Test
    @DisplayName("User 객체 생성 테스트")
    void 유저_정상_생성() {
        assertThatCode(
            () -> User.builder()
                .userId("test")
                .name("test")
                .address("test")
                .password("test")
                .email("test")
                .authority(1)
                .salt("test")
                .build()
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @EmptySource
    @DisplayName("User 객체 생성 실패 테스트 : 아이디 오류")
    void 유저_생성_실패_아이디_오류(String userId) {
        assertThatCode(
            () -> User.builder()
                .userId(userId)
                .name("test")
                .address("test")
                .password("test")
                .email("test")
                .authority(1)
                .salt("test")
                .build()
        )
            .isInstanceOf(UserException.class)
            .hasMessage("유저의 아이디는 필수 값입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @EmptySource
    @DisplayName("User 객체 생성 실패 테스트 : 이름 오류")
    void 유저_생성_실패_이름_오류(String name) {
        assertThatCode(
            () -> User.builder()
                .userId("test")
                .name(name)
                .address("test")
                .password("test")
                .email("test")
                .authority(1)
                .salt("test")
                .build()
        ).hasMessageContaining("유저의 이름은 필수 값입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @EmptySource
    @DisplayName("User 객체 생성 실패 테스트 : 주소 오류")
    void 유저_생성_실패_주소_오류(String address) {
        assertThatCode(
            () -> User.builder()
                .userId("test")
                .name("test")
                .address(address)
                .password("test")
                .email("test")
                .authority(1)
                .salt("test")
                .build()
        ).hasMessageContaining("유저의 주소는 필수 값입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @EmptySource
    @DisplayName("User 객체 생성 실패 테스트 : 비밀번호 오류")
    void 유저_생성_실패_비밀번호_오류(String password) {
        assertThatCode(
            () -> User.builder()
                .userId("test")
                .name("test")
                .address("test")
                .password(password)
                .email("test")
                .authority(1)
                .salt("test")
                .build()
        ).hasMessageContaining("유저의 비밀번호는 필수 값입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @EmptySource
    @DisplayName("User 객체 생성 실패 테스트 : 이메일 오류")
    void 유저_생성_실패_이메일_오류(String email) {
        assertThatCode(
            () -> User.builder()
                .userId("test")
                .name("test")
                .address("test")
                .password("test")
                .email(email)
                .authority(1)
                .salt("test")
                .build()
        ).hasMessageContaining("유저의 이메일은 필수 값입니다.");
    }
}

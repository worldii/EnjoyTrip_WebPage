package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.global.error.UserException;
import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@DisplayName("유저 서비스 통합 테스트")
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("정상적으로 유저가 회원가입할 수 있다.")
    void 유저_회원가입_정상적으로_생성() {
        //given
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha2")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .salt("test")
            .build();

        //when
        boolean join = userService.join(userAddRequest);

        //then
        assertThat(join).isTrue();
    }

    @Test
    @DisplayName("유저의 이메일이 다르면 정상적으로 회원가입할 수 없다")
    void 유저_회원가입_포맷이_다르면_생성_실패() {
        //given
        String wrongEmail = "";

        //when
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email(wrongEmail)
            .authority(1)
            .salt("test")
            .build();

        //then
        assertThatCode(() -> userService.join(userAddRequest))
            .isInstanceOf(UserException.class)
            .hasMessage("유저의 이메일은 필수 값입니다.");
    }

    @Test
    @DisplayName("유저의 이름이 다르면 정상적으로 회원가입할 수 없다")
    void 유저_회원가입_이름이_다르면_생성_실패() {
        //given
        String wrongName = "";

        //when
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId("jongha")
            .name(wrongName)
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .salt("test")
            .build();

        //then
        assertThatCode(() -> userService.join(userAddRequest))
            .isInstanceOf(UserException.class)
            .hasMessage("유저의 이름은 필수 값입니다.");
    }

    @Test
    @DisplayName("유저의 아이디가 다르면 정상적으로 회원가입할 수 없다")
    void 유저_회원가입_아이디가_다르면_생성_실패() {
        //given
        String wrongUserId = "";

        //when
        UserAddRequest userAddRequest = UserAddRequest.builder()
            .userId(wrongUserId)
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .salt("test")
            .build();

        //then
        assertThatCode(() -> userService.join(userAddRequest))
            .isInstanceOf(UserException.class)
            .hasMessage("유저의 아이디는 필수 값입니다.");
    }
}

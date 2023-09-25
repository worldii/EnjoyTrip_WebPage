package com.ssafy.enjoytrip.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ssafy.enjoytrip.global.error.UserException;
import com.ssafy.enjoytrip.user.model.dto.request.UserAddRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserLoginRequest;
import com.ssafy.enjoytrip.user.model.dto.request.UserModifyRequest;
import com.ssafy.enjoytrip.user.model.dto.response.UserResponse;
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
            .build();

        //then
        assertThatCode(() -> userService.join(userAddRequest))
            .isInstanceOf(UserException.class)
            .hasMessage("유저의 아이디는 필수 값입니다.");
    }

    @Test
    @DisplayName("유저가 정상적으로 로그인할 수 있다")
    void 유저_정상_로그인() {
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

        // when
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("test")
            .build();

        // then
        assertThatCode(() -> userService.login(userLoginRequest))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("유저가 비밀번호가 틀리면 로그인할 수 없다")
    void 유저_비밀번호_틀림_로그인_실패() {
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

        // when
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userId("jongha")
            .password("wrongPassword")
            .build();

        // then
        assertThatCode(() -> userService.login(userLoginRequest))
            .isInstanceOf(UserException.class)
            .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("유저의 정보를 조회할 수 있다")
    void 유저_정보_조회() {
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

        // when
        UserResponse userInformation = userService.getInformation(userAddRequest.getUserId());

        // then
        assertAll(
            () -> assertThat(userInformation.getName()).isEqualTo("jongha"),
            () -> assertThat(userInformation.getAddress()).isEqualTo("test"),
            () -> assertThat(userInformation.getEmail()).isEqualTo("test")
        );
    }

    @Test
    @DisplayName("유저의 정보를 수정할 수 있다")
    void 유저_정보_수정() {
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

        // when
        UserModifyRequest userModifyRequest = UserModifyRequest.builder()
            .name("jongha2")
            .address("test2")
            .password("test2")
            .email("test2")
            .build();
        userService.modify(userModifyRequest, userAddRequest.getUserId());

        // then
        UserResponse userInfomation = userService.getInformation(userAddRequest.getUserId());
        assertAll(
            () -> assertThat(userInfomation.getName()).isEqualTo("jongha2"),
            () -> assertThat(userInfomation.getAddress()).isEqualTo("test2"),
            () -> assertThat(userInfomation.getPassword()).isEqualTo("test2"),
            () -> assertThat(userInfomation.getEmail()).isEqualTo("test2")
        );
    }

    @Test
    @DisplayName("해당 유저가 존재하지 않을 때 유저의 정보를 다른 사람이 수정할 수 없다")
    void 유저_정보_수정_할수_없다_유저가_존재하지_않을때() {
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

        // when
        UserModifyRequest userModifyRequest = UserModifyRequest.builder()
            .name("jongha2")
            .address("test2")
            .password("test2")
            .email("test2")
            .build();

        // then
        assertThatCode(() -> userService.modify(userModifyRequest, "test2"))
            .isInstanceOf(UserException.class)
            .hasMessage("해당 유저가 없습니다.");
    }

    @Test
    @DisplayName("현재 로그인한 유저의 정보를 삭제할 수 있다")
    void 유저_정보_삭제() {
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

        // when
        userService.delete(userAddRequest.getUserId(), userAddRequest.getUserId());

        // then
        assertThatCode(() -> userService.getInformation(userAddRequest.getUserId()))
            .isInstanceOf(UserException.class)
            .hasMessage("해당 유저가 없습니다.");
    }

    @Test
    @DisplayName("현재 로그인한 유저가 아닌 다른 유저의 정보를 삭제할 수 없다")
    void 유저_정보_삭제_할수_없다_현재_로그인한_유저가_아닌_경우() {
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

        // when & then
        assertThatCode(() -> userService.delete(userAddRequest.getUserId(), "test2"))
            .isInstanceOf(UserException.class)
            .hasMessage("로그인한 회원이 아닙니다.");
    }
}

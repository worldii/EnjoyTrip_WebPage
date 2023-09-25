package com.ssafy.enjoytrip.integration.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.ssafy.enjoytrip.user.model.entity.User;
import com.ssafy.enjoytrip.user.model.mapper.UserMapper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("유저 매퍼 통합 테스트")
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("정상적으로 유저가 회원가입할 수 있다.")
    void 유저_정상적으로_생성() {
        //given
        User user = User.builder()
            .userId("jongha")
            .name("jongha")
            .address("test")
            .password("test")
            .email("test")
            .authority(1)
            .salt("test")
            .build();

        //when & then
        assertThatCode(() -> userMapper.insertByUser(user))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("유저를 selectById로 조회할 수 있다.")
    void 유저_정상적으로_조회() {
        //given
        String userId = "test";

        //when
        User user = userMapper.selectByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        //then
        assertThatCode(() -> user.getUserId().equals(userId))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("유저를 updateByUser 수정할 수 있다.")
    void 유저_정상적으로_수정() {
        // given
        User oldUser = userMapper.selectByUserId("test").get();

        // when
        oldUser.updateAddress("test2");
        userMapper.updateByUser(oldUser);
        User newUser = userMapper.selectByUserId("test").get();

        // then
        assertThat(newUser.getAddress()).isEqualTo("test2");
    }

    @Test
    @DisplayName("유저를 deleteByUserId로 삭제할 수 있다.")
    void 유저_정상적으로_삭제() {
        // given
        String userId = "test";

        // when
        userMapper.deleteByUserId(userId);

        // then
        Optional<User> user = userMapper.selectByUserId(userId);
        assertThat(user).isEmpty();
    }
}

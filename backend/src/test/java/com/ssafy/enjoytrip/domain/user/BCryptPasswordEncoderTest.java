package com.ssafy.enjoytrip.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ssafy.enjoytrip.user.model.interceptor.BCryptPasswordEncoder;
import com.ssafy.enjoytrip.user.model.interceptor.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BCryptPasswordEncoder 테스트")
class BCryptPasswordEncoderTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @DisplayName("hash 가 되면 비밀번호를 다르게 암호화 한다.")
    void testHashPassword() {
        //given
        String raw1 = "test";
        String raw2 = "test";

        //when
        String hashed1 = passwordEncoder.hashPassword(raw1);
        String hashed2 = passwordEncoder.hashPassword(raw2);

        //then
        assertNotNull(hashed1);
        assertNotNull(hashed2);
        assertNotEquals(hashed1, hashed2);
    }

    @Test
    @DisplayName("raw 와 hashed 가 주어지면 같은 비밀번호인지 확인한다.")
    void testIsMatch() {
        //given
        String raw = "test";
        String hashed = passwordEncoder.hashPassword(raw);

        //when
        boolean result = passwordEncoder.isMatch(raw, hashed);

        //then
        assertEquals(true, result);
    }
}

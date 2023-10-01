package com.ssafy.enjoytrip.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ssafy.enjoytrip.infra.BCryptPasswordEncoder;
import com.ssafy.enjoytrip.infra.PasswordEncoder;
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
    @DisplayName("salt 가 주어지면 hash 된 비밀번호를 반환한다.")
    void testHashPassword() {
        //given
        String salt = passwordEncoder.generateSalt();
        String raw1 = "test";
        String raw2 = "test";

        //when
        String hashed1 = passwordEncoder.hashPassword(raw1, salt);
        String hashed2 = passwordEncoder.hashPassword(raw2, salt);

        //then
        assertNotNull(hashed1);
        assertNotNull(hashed2);
        assertEquals(hashed1, hashed2);
    }

    @Test
    @DisplayName("raw 와 hashed 가 주어지면 같은 비밀번호인지 확인한다.")
    void testIsMatch() {
        //given
        String salt = passwordEncoder.generateSalt();
        String raw = "test";
        String hashed = passwordEncoder.hashPassword(raw, salt);

        //when
        boolean result = passwordEncoder.isMatch(raw, hashed, salt);

        //then
        assertEquals(true, result);
    }
}

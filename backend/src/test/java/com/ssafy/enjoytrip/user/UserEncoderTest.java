package com.ssafy.enjoytrip.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ssafy.enjoytrip.user.model.interceptor.UserEncoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserEncoderTest {

    @Autowired
    UserEncoder userEncoder;

    @Test
    public void testLoadUserEncoder() {
        //given-when-then
        assertNotNull(userEncoder);
    }

    @Test
    public void testHashPassword() {
        //given
        String raw1 = "test";
        String raw2 = "test";

        //when
        String hashed1 = userEncoder.hashPassword(raw1);
        String hashed2 = userEncoder.hashPassword(raw2);

        //then
        System.out.println("hashed1 = " + hashed1);
        System.out.println("hashed2 = " + hashed2);

        assertNotNull(hashed1);
        assertNotNull(hashed2);
        assertNotEquals(hashed1, hashed2);
    }

    @Test
    public void testIsMatch() {
        //given
        String raw = "test";
        String hashed = userEncoder.hashPassword(raw);

        //when
        boolean result = userEncoder.isMatch(raw, hashed);

        //then
        System.out.println("raw = " + raw);
        System.out.println("hashed = " + hashed);

        assertEquals(result, true);
    }
}

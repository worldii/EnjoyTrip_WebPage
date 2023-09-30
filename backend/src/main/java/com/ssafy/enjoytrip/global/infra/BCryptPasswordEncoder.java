package com.ssafy.enjoytrip.global.infra;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String generateSalt() {
        return BCrypt.gensalt();
    }

    @Override
    public String hashPassword(final String raw, final String salt) {
        return BCrypt.hashpw(raw, salt);
    }

    @Override
    public boolean isMatch(final String raw, final String hashed, final String salt) {
        return hashPassword(raw, salt).equals(hashed);
    }
}

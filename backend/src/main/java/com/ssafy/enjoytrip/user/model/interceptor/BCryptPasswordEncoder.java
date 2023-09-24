package com.ssafy.enjoytrip.user.model.interceptor;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String hashPassword(final String raw) {
        return BCrypt.hashpw(raw, BCrypt.gensalt());
    }

    @Override
    public boolean isMatch(final String raw, final String hashed) {
        return BCrypt.checkpw(raw, hashed);
    }
}

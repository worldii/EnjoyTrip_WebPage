package com.ssafy.enjoytrip.core.user.model.dto.response;

import com.ssafy.enjoytrip.core.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String userId;
    private String name;
    private String address;
    private String password;
    private String email;
    private int authority;
    private String salt;

    public static UserResponse from(final User user) {
        return new UserResponse(
            user.getUserId(), user.getName(),
            user.getAddress(), user.getPassword(),
            user.getEmail(), user.getAuthority(),
            user.getSalt()
        );
    }
}

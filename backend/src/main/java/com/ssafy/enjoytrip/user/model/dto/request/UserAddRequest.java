package com.ssafy.enjoytrip.user.model.dto.request;

import com.ssafy.enjoytrip.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddRequest {

    private String userId;
    private String name;
    private String address;
    private String password;
    private String email;
    private int authority;
    private String salt;

    public User toEntity() {
        return User.builder()
            .userId(userId)
            .name(name)
            .address(address)
            .password(password)
            .email(email)
            .authority(authority)
            .salt(salt)
            .build();
    }
}

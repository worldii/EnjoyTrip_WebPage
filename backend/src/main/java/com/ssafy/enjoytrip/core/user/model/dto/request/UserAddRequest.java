package com.ssafy.enjoytrip.core.user.model.dto.request;

import com.ssafy.enjoytrip.core.user.model.entity.User;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddRequest {

    @NotBlank(message = "유저 아이디는 필수 입력 값입니다.")
    private String userId;
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;
    private String address;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;
    private int authority;

    public User toEntity(final String hashedPassword, final String salt) {
        return User.builder()
            .userId(userId)
            .name(name)
            .address(address)
            .password(hashedPassword)
            .email(email)
            .authority(authority)
            .salt(salt)
            .build();
    }
}

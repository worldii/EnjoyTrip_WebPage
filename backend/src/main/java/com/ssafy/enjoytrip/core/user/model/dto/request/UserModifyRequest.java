package com.ssafy.enjoytrip.core.user.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModifyRequest {

    private String name;
    private String address;
    private String password;
    private String email;
}

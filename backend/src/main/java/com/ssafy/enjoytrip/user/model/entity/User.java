package com.ssafy.enjoytrip.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class User {

    private String userId;
    private String name;
    private String address;
    private String password;
    private String email;
    private int authority;
    private String salt;
}

package com.ssafy.enjoytrip.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
public class User {
    private String userId;
    private String name;
    private String address;
    private String password;
    private String email;
    private int authority;
    private String salt;
}

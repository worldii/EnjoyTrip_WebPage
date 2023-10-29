package com.ssafy.enjoytrip.core.board.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BoardSearchRequest {

    private String category;
    private String keyword;
    private Integer page = 1;
    private Integer pageSize = 10;
}

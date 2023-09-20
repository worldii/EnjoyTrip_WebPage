package com.ssafy.enjoytrip.board.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardSearchRequest {

    private Integer pageNum;
    private Integer pageSize;
    private String searchType;
    private String searchContent;
}

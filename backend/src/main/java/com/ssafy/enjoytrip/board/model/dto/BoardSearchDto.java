package com.ssafy.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardSearchDto {
    private Integer pageNum;
    private Integer pageSize;
    private String searchType;
    private String searchContent;
}

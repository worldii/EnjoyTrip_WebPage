package com.ssafy.enjoytrip.board.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "게시글 검색 정보", description = "게시글 검색 정보")
public class BoardSearchDto {
    @ApiModelProperty(value = "페이지 숫자")
    private Integer pageNum;
    @ApiModelProperty(value = "페이지 사이즈")
    private Integer pageSize;
    @ApiModelProperty(value = "검색 타입")
    private String searchType;
    @ApiModelProperty(value = "검색 내용")
    private String searchContent;
}

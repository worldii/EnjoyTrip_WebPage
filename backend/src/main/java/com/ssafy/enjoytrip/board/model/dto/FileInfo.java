package com.ssafy.enjoytrip.board.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Data
@ApiModel(value = "파일 정보", description = "파일 정보")
public class FileInfo {
    @ApiModelProperty(value = "파일 번호")
    private int FileInfoId;
    @ApiModelProperty(value = "게시글 번호")
    @NotNull(message = "boardID는 꼭 존재하여야 합니다.")
    private int boardId;
    @ApiModelProperty(value = "파일 url")
    private String fileUrl;

}

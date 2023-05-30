package com.ssafy.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Data
public class FileInfo {
    private int FileInfoId;
    @NotNull(message = "boardID는 꼭 존재하여야 합니다.")
    private int boardId;
    private String fileUrl;

}

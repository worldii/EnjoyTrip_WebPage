package com.ssafy.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FileInfo {

    private Long fileInfoId;
    private Long boardId;
    private String fileUrl;
}

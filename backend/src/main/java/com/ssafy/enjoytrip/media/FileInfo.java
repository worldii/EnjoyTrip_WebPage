package com.ssafy.enjoytrip.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FileInfo {

    private Long fileInfoId;
    private Long boardId;
    private String fileUrl;

    @Builder
    public FileInfo(Long boardId, String fileUrl) {
        this.boardId = boardId;
        this.fileUrl = fileUrl;
    }
}

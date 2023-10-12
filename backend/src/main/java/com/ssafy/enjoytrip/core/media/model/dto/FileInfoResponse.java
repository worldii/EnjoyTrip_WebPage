package com.ssafy.enjoytrip.core.media.model.dto;

import com.ssafy.enjoytrip.core.media.model.entity.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class FileInfoResponse {

    private Long fileInfoId;
    private Long boardId;
    private String fileUrl;

    public static FileInfoResponse from(final FileInfo fileInfo) {
        return new FileInfoResponse(
            fileInfo.getFileInfoId(),
            fileInfo.getBoardId(),
            fileInfo.getFileUrl()
        );
    }

    public FileInfo toEntity() {
        return FileInfo.builder()
            .fileInfoId(fileInfoId)
            .boardId(boardId)
            .fileUrl(fileUrl)
            .build();
    }
}

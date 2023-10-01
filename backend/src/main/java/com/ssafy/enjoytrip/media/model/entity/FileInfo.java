package com.ssafy.enjoytrip.media.model.entity;

import com.ssafy.enjoytrip.global.error.MediaException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileInfo {

    private Long fileInfoId;
    private Long boardId;
    private String fileUrl;

    @Builder
    public FileInfo(final Long fileInfoId, final Long boardId, final String fileUrl) {
        validateBoardId(boardId);
        validateFileUrl(fileUrl);
        this.fileInfoId = fileInfoId;
        this.boardId = boardId;
        this.fileUrl = fileUrl;
    }

    private void validateFileUrl(final String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            throw new MediaException("file의 url은 null이거나 비어있을 수 없습니다.");
        }
    }

    private void validateBoardId(final Long boardId) {
        if (boardId == null) {
            throw new MediaException("boardId는 null이 될 수 없습니다.");
        }
    }

    public static FileInfo of(final Long boardId, final String fileUrl) {
        return FileInfo.builder()
            .boardId(boardId)
            .fileUrl(fileUrl)
            .build();
    }

}

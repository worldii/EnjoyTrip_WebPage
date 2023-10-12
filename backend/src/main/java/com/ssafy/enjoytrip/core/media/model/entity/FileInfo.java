package com.ssafy.enjoytrip.core.media.model.entity;

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
    private String userId;

    @Builder
    public FileInfo(final Long fileInfoId, final Long boardId, final String fileUrl,
        final String userId) {
        validateBoardId(boardId);
        validateUserId(userId);
        validateFileUrl(fileUrl);
        this.fileInfoId = fileInfoId;
        this.userId = userId;
        this.boardId = boardId;
        this.fileUrl = fileUrl;
    }

    private void validateUserId(final String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new MediaException("userId는 null이거나 비어있을 수 없습니다.");
        }
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

    public static FileInfo of(final Long boardId, final String fileUrl, final String userId) {
        return FileInfo.builder()
            .userId(userId)
            .boardId(boardId)
            .fileUrl(fileUrl)
            .build();
    }

}

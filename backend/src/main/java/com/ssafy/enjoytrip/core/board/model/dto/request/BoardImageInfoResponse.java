package com.ssafy.enjoytrip.core.board.model.dto.request;

import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class BoardImageInfoResponse {

    private Long fileInfoId;
    private Long boardId;
    private String imageUrl;

    public static BoardImageInfoResponse from(final BoardImageInfo boardImageInfo) {
        return new BoardImageInfoResponse(
            boardImageInfo.getImageInfoId(),
            boardImageInfo.getBoardId(),
            boardImageInfo.getImageUrl()
        );
    }

    public BoardImageInfo toEntity() {
        return BoardImageInfo.builder()
            .imageInfoId(fileInfoId)
            .boardId(boardId)
            .imageUrl(imageUrl)
            .build();
    }
}

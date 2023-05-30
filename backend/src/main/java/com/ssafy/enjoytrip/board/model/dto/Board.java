package com.ssafy.enjoytrip.board.model.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Board {
    private int boardId;
    private String userId;
    private String subject;
    private String content;
    private int hit;
    private String currentUpdate;
    private BoardType boardType;
    private List<FileInfo> imageFiles;

    public Board(BoardRequestDto boardRequestDto, String userId) {
        this.userId = userId;
        this.subject = boardRequestDto.getSubject();
        this.content = boardRequestDto.getContent();
        this.boardType = boardRequestDto.getBoardType();
    }
}

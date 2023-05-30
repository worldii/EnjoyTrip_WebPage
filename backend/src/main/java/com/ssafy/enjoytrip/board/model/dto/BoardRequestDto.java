package com.ssafy.enjoytrip.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private int boardId;
    @NotBlank(message = "제목을 입력해주세요.")
    private String subject;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private String userId;
    private BoardType boardType;

    // 첨부이미지
    private List<FileInfo> imageFiles;

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public void setImages(List<FileInfo> imageFiles) {
        this.imageFiles = imageFiles;
    }

    public BoardRequestDto(int boardId, String subject, String content, String userId, BoardType boardType) {
        this.boardId = boardId;
        this.subject = subject;
        this.content = content;
        this.userId = userId;
        this.boardType = boardType;
    }

    public BoardRequestDto(String subject, String content, String userId, BoardType boardType) {
        this.subject = subject;
        this.content = content;
        this.boardType = boardType;
        this.userId = userId;
    }

    public Board toEntity() {
        return Board.builder()
                .boardId(boardId)
                .subject(subject)
                .content(content)
                .boardType(boardType)
                .imageFiles(imageFiles)
                .userId(userId)
                .build();
    }
}

package com.ssafy.enjoytrip.board.model.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "게시글 요청 정보", description = "게시글 요청 정보")
public class BoardRequestDto {
    @ApiModelProperty(value = "게시글 번호")
    private int boardId;
    @ApiModelProperty(value = "게시글 제목")
    @NotBlank(message = "제목을 입력해주세요.")
    private String subject;
    @ApiModelProperty(value = "게시글 내용")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    @ApiModelProperty(value = "작성자 아이디")
    private String userId;
    @ApiModelProperty(value = "게시글 타입")
    private BoardType boardType;
    @ApiModelProperty(value = "게시글 이미지")
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

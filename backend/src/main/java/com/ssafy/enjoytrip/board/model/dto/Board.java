package com.ssafy.enjoytrip.board.model.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(value = "게시글 정보", description = "게시글 정보")
public class Board {
    @ApiModelProperty(value = "게시글 번호")
    private int boardId;
    @ApiModelProperty(value = "작성자 아이디")
    private String userId;
    @ApiModelProperty(value = "게시글 제목")
    private String subject;
    @ApiModelProperty(value = "게시글 내용")
    private String content;
    @ApiModelProperty(value = "게시글 조회수")
    private int hit;
    @ApiModelProperty(value = "게시글 수정일")
    private String currentUpdate;
    @ApiModelProperty(value = "게시글 타입")
    private BoardType boardType;
    @ApiModelProperty(value = "게시글 이미지")
    private List<FileInfo> imageFiles;

    public Board(BoardRequestDto boardRequestDto, String userId) {
        this.userId = userId;
        this.subject = boardRequestDto.getSubject();
        this.content = boardRequestDto.getContent();
        this.boardType = boardRequestDto.getBoardType();
    }
}

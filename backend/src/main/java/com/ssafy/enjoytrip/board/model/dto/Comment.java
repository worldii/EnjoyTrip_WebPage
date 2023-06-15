package com.ssafy.enjoytrip.board.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(value = "댓글 정보", description = "댓글 정보")
public class Comment {
    @ApiModelProperty(value = "댓글 번호")
    private int commentId;
    @ApiModelProperty(value = "게시글 번호")
    private int boardId;
    @ApiModelProperty(value = "작성자 아이디")
    private String userId;
    @ApiModelProperty(value = "댓글 내용")
    private String content;
    @ApiModelProperty(value = "댓글 수정일")
    private String currentUpdate;

    public Comment (CommentRequestDto commentRequestDto, String userId, int boardId) {
        this.userId = userId;
        this.content = commentRequestDto.getContent();
        this.boardId = boardId;
    }
}

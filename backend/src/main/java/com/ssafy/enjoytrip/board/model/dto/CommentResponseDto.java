package com.ssafy.enjoytrip.board.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value = "댓글 응답 정보", description = "댓글 응답 정보")
public class CommentResponseDto {

    @ApiModelProperty(value = "댓글 번호")
    private int commentId;
    @ApiModelProperty(value = "작성자 아이디")
    private String userId;
    @ApiModelProperty(value = "댓글 내용")
    private String content;
    @ApiModelProperty(value = "댓글 수정일")
    private String currentUpdate;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.currentUpdate = comment.getCurrentUpdate();
    }
}

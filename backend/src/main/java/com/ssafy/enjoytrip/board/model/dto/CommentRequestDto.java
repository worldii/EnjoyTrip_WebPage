package com.ssafy.enjoytrip.board.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "댓글 요청 정보", description = "댓글 요청 정보")
public class CommentRequestDto {
    @ApiModelProperty(value = "댓글 번호")
    private int commentId;
    @ApiModelProperty(value = "게시글 번호")
    @NotBlank(message = "게시글 입력이 비어있습니다.")
    private int boardId;
    @ApiModelProperty(value = "작성자 아이디")
    @NotBlank(message = "유저 입력이 비어있습니다.")
    private String userId;
    @ApiModelProperty(value = "댓글 내용")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Comment toEntity() {
        return Comment.builder()
                .commentId(commentId)
                .boardId(boardId)
                .userId(userId)
                .content(content)
                .build();
    }

}

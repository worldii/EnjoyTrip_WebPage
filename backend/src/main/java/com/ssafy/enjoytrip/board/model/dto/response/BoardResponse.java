package com.ssafy.enjoytrip.board.model.dto.response;

import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.BoardType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "게시글 응답 정보", description = "게시글 응답 정보")
public class BoardResponse {
    @ApiModelProperty(value = "게시글 번호")
    private int boardId;
    @ApiModelProperty(value = "작성자 아이디")
    private String userId;
    @ApiModelProperty(value = "게시글 제목")
    private String subject;
    @ApiModelProperty(value = "게시글 내용")
    private String content;
    @ApiModelProperty(value = "게시글 타입")
    private BoardType boardType;
    @ApiModelProperty(value = "게시글 조회수")
    private int hit;
    @ApiModelProperty(value = "게시글 수정일")
    private String currentUpdate;

    public BoardResponse(Board board) {
        this.boardId = board.getBoardId();
        this.userId = board.getUserId();
        this.subject = board.getSubject();
        this.content = board.getContent();
        this.hit = board.getHit();
        this.currentUpdate = board.getCurrentUpdate();
        this.boardType = board.getBoardType();
    }
}

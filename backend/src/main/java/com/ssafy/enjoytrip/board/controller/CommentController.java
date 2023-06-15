package com.ssafy.enjoytrip.board.controller;

import com.ssafy.enjoytrip.board.model.dto.CommentRequestDto;
import com.ssafy.enjoytrip.board.model.dto.CommentResponseDto;
import com.ssafy.enjoytrip.board.service.CommentService;
import com.ssafy.enjoytrip.jwt.model.dto.NoAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ssafy.enjoytrip.util.ApiUtil.*;

@Api(tags = {"댓글 API 정보를 제공하는 controller"})
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://127.0.0.1:8080", "http://192.168.0.1:8080", "http://localhost:8080"})
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @ApiOperation(value = "댓글 리스트 조회", notes = "댓글 리스트 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", required = true, dataType = "int", paramType = "path")
    })
    @NoAuth
    @GetMapping("/{boardId}")
    public ApiResult<List<CommentResponseDto>> getCommentList(@PathVariable int boardId) {
        List<CommentResponseDto> commentList = commentService.getCommentList(boardId);
        return success(commentList);
    }

    @ApiOperation(value = "댓글 등록", notes = "댓글 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "commentRequestDto", value = "댓글 정보", required = true, dataType = "CommentRequestDto", paramType = "body")
    })
    @PostMapping("/{boardId}")
    public ApiResult<?> registerComment(@PathVariable int boardId, @RequestBody CommentRequestDto commentRequestDto, BindingResult result) {
        if (result.hasErrors()) return error(result);
        commentService.regist(commentRequestDto, commentRequestDto.getUserId(), boardId);
        return success(true);
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "댓글 번호", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "commentRequestDto", value = "댓글 정보", required = true, dataType = "CommentRequestDto", paramType = "body")
    })
    @PutMapping("/{commentId}")
    public ApiResult<Boolean> modifyComment(@PathVariable int commentId, @RequestBody CommentRequestDto commentRequestDto) {

        commentService.modify(commentId, commentRequestDto);
        return success(true);
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "commentId", value = "댓글 번호", required = true, dataType = "int", paramType = "path")
    })
    @DeleteMapping("/{boardId}/{commentId}")
    public ApiResult<Boolean> deleteComment(@PathVariable int commentId) {
        commentService.delete(commentId);
        return success(true);
    }
}

package com.ssafy.enjoytrip.board.controller;

import com.ssafy.enjoytrip.board.model.dto.CommentRequestDto;
import com.ssafy.enjoytrip.board.model.dto.CommentResponseDto;
import com.ssafy.enjoytrip.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ssafy.enjoytrip.util.ApiUtil.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://127.0.0.1:8080", "http://192.168.0.1:8080", "http://localhost:8080"})
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{boardId}")
    public ApiResult<List<CommentResponseDto>> getCommentList(@PathVariable int boardId) {
        List<CommentResponseDto> commentList = commentService.getCommentList(boardId);
        return success(commentList);
    }

    @PostMapping("/{boardId}")
    public ApiResult<?> registerComment(@PathVariable int boardId, @RequestBody CommentRequestDto commentRequestDto, BindingResult result) {
        if (result.hasErrors()) return error(result);
        commentService.regist(commentRequestDto, commentRequestDto.getUserId(), boardId);
        return success(true);
    }

    @PutMapping("/{commentId}")
    public ApiResult<Boolean> modifyComment(@PathVariable int commentId, @RequestBody CommentRequestDto commentRequestDto) {

        commentService.modify(commentId, commentRequestDto);
        return success(true);
    }

    @DeleteMapping("/{boardId}/{commentId}")
    public ApiResult<Boolean> deleteComment(@PathVariable int commentId) {
        commentService.delete(commentId);
        return success(true);
    }
}

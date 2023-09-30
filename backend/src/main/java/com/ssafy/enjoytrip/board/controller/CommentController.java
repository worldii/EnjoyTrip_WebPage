package com.ssafy.enjoytrip.board.controller;

import com.ssafy.enjoytrip.board.model.dto.request.CommentModifyRequest;
import com.ssafy.enjoytrip.board.model.dto.request.CommentSaveRequest;
import com.ssafy.enjoytrip.board.model.dto.response.CommentResponse;
import com.ssafy.enjoytrip.board.service.CommentService;
import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public ResponseEntity<Long> registerComment(
        @PathVariable final Long boardId,
        @RequestBody final CommentSaveRequest commentSaveRequest,
        final String userId,
        final BindingResult result
    ) {
        // TODO : bindingResult 사용해서 에러 처리
        Long commentId = commentService.save(
            commentSaveRequest,
            userId,
            boardId
        );
        return ResponseEntity.ok(commentId);
    }

    @NoAuth
    @GetMapping("/{boardId}")
    public ResponseEntity<List<CommentResponse>> getCommentList(@PathVariable final Long boardId) {
        return ResponseEntity.ok(commentService.getCommentList(boardId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> modifyComment(
        @PathVariable final Long commentId,
        @RequestBody final CommentModifyRequest request
    ) {
        commentService.modify(commentId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable final Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }
}

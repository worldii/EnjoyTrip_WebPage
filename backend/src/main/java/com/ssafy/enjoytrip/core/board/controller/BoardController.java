package com.ssafy.enjoytrip.core.board.controller;

import com.ssafy.enjoytrip.core.board.model.dto.request.BoardModifyRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSaveRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSearchRequest;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardDetailResponse;
import com.ssafy.enjoytrip.core.board.service.BoardService;
import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import com.ssafy.enjoytrip.global.dto.PageResponse;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Long> save(
        @RequestBody @Valid final BoardSaveRequest request,
        @LoginUser final String userId
    ) {
        final Long boardId = boardService.saveBoard(request, userId);

        return ResponseEntity.created(URI.create("/board/" + boardId)).body(boardId);
    }

    @NoAuth
    @GetMapping("/list")
    public ResponseEntity<PageResponse> getList(
        @ModelAttribute final BoardSearchRequest request
    ) {
        return ResponseEntity.ok(boardService.getBoardList(request));
    }

    @NoAuth
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponse> getBoard(
        @PathVariable("boardId") final Long boardId
    ) {
        return ResponseEntity.ok(boardService.detail(boardId));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<Void> modifyBoard(
        @PathVariable final Long boardId,
        @LoginUser final String userId,
        @RequestBody final BoardModifyRequest boardModifyRequest
    ) {
        boardService.modify(boardId, userId, boardModifyRequest);
        return ResponseEntity.ok().build();
    }

    @NoAuth
    @PutMapping("/view/{boardId}")
    public ResponseEntity<Boolean> updateHit(@PathVariable final Long boardId) {
        boardService.updateHit(boardId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
        @PathVariable final Long boardId,
        @LoginUser final String userId
    ) {
        boardService.delete(boardId, userId);
        return ResponseEntity.noContent().build();
    }
}

package com.ssafy.enjoytrip.core.board.controller;

import com.ssafy.enjoytrip.core.board.model.dto.request.PageInfoRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.SearchCondition;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardDetailResponse;
import com.ssafy.enjoytrip.core.board.model.dto.response.PageResponse;
import com.ssafy.enjoytrip.core.board.service.BoardService;
import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Long> save(
        @RequestParam("json") final String json,
        @RequestParam("files") @Nullable final List<MultipartFile> files,
        @LoginUser final String userId
    ) {
        final Long boardId = boardService.saveBoard(json, files, userId);

        return ResponseEntity.created(URI.create("/board/" + boardId)).body(boardId);
    }

    @NoAuth
    @GetMapping("/list/{currentPage}")
    public ResponseEntity<PageResponse> getListByPage(@PathVariable final Integer currentPage) {

        return ResponseEntity.ok(boardService.getBoardList(PageInfoRequest.from(currentPage)));
    }

    @NoAuth
    @GetMapping("/list/search")
    public ResponseEntity<PageResponse> getListBySearchDto(
        @RequestBody final PageInfoRequest pageInfoRequest,
        @ModelAttribute final SearchCondition searchCondition
    ) {
        return ResponseEntity.ok(
            boardService.getBoardListBySearchDto(searchCondition, pageInfoRequest));
    }

    @NoAuth
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponse> getBoard(
        @PathVariable("boardId") final Long boardId) {
        return ResponseEntity.ok(boardService.detail(boardId));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<Void> modifyBoard(
        @PathVariable final Long boardId,
        @LoginUser final String userId,
        @RequestParam("json") final String boardModifyRequest,
        @RequestParam("files") @Nullable final List<MultipartFile> files
    ) {
        boardService.modify(boardId, userId, boardModifyRequest, files);
        return ResponseEntity.ok().build();
    }

    @NoAuth
    @PostMapping("/hit/{boardId}")
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
        return ResponseEntity.ok().build();
    }
}

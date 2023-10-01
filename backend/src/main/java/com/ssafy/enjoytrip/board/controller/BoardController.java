package com.ssafy.enjoytrip.board.controller;

import com.ssafy.enjoytrip.board.model.dto.request.BoardModifyRequest;
import com.ssafy.enjoytrip.board.model.dto.request.PageInfoRequest;
import com.ssafy.enjoytrip.board.model.dto.request.SearchDto;
import com.ssafy.enjoytrip.board.model.dto.response.BoardResponse;
import com.ssafy.enjoytrip.board.model.dto.response.PageResponse;
import com.ssafy.enjoytrip.board.service.BoardService;
import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
        @RequestParam @Valid final String json,
        final List<MultipartFile> files,
        final @LoginUser String userId
    ) {
        
        return ResponseEntity.ok(boardService.saveBoard(json, files, userId));
    }

    @NoAuth
    @GetMapping
    public ResponseEntity<PageResponse> getList(
        final PageInfoRequest pageInfoRequest,
        final HttpServletRequest request
    ) {
        return ResponseEntity.ok(
            boardService.getBoardList(pageInfoRequest,
                request.getContextPath() + "/board/list?page")
        );
    }

    @NoAuth
    @GetMapping("/list/{currentPage}")
    public ResponseEntity<PageResponse> getListByPage(
        final PageInfoRequest pageInfoRequest,
        final HttpServletRequest request
    ) {
        final PageResponse listByPage = boardService.getListByPage(
            pageInfoRequest,
            request.getContextPath() + "/board/list?page"
        );

        return ResponseEntity.ok(listByPage);
    }

    @NoAuth
    @GetMapping("/list/search")
    public ResponseEntity<PageResponse> getListBySearchDto(
        final PageInfoRequest pageInfoRequest,
        @ModelAttribute final SearchDto searchDto,
        final HttpServletRequest request
    ) {
        final PageResponse boardResponse = boardService.getBoardListBySearchDto(
            searchDto, pageInfoRequest, request.getContextPath() + "/board/list/search?page");

        return ResponseEntity.ok(boardResponse);
    }

    @NoAuth
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable("boardId") final Long boardId) {
        return ResponseEntity.ok(BoardResponse.from(boardService.detail(boardId)));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<Void> modifyBoard(
        @PathVariable final Long boardId,
        @RequestBody @Valid final BoardModifyRequest boardModifyRequest,
        @RequestParam final String userId
    ) {
        boardService.modify(boardId, userId, boardModifyRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
        @PathVariable final Long boardId,
        @RequestParam final String userId
    ) {
        boardService.delete(boardId, userId);
        return ResponseEntity.ok().build();
    }

    @NoAuth
    @PostMapping("/hit/{boardId}")
    public ResponseEntity<Boolean> updateHit(@PathVariable final Long boardId) {
        boardService.updateHit(boardId);
        return ResponseEntity.ok().build();
    }
}

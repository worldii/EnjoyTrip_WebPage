package com.ssafy.enjoytrip.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.enjoytrip.board.model.dto.*;
import com.ssafy.enjoytrip.board.service.BoardService;
import com.ssafy.enjoytrip.board.service.FileService;
import com.ssafy.enjoytrip.jwt.model.dto.NoAuth;
import com.ssafy.enjoytrip.util.PageNavigationForPageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ssafy.enjoytrip.util.ApiUtil.ApiResult;
import static com.ssafy.enjoytrip.util.ApiUtil.success;

@Api(tags = {"게시글 API 정보를 제공하는 controller"})
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://127.0.0.1:8080", "http://192.168.0.1:8080", "http://localhost:8080"})
@RequestMapping("/board")
public class BoardRestController {

    private final BoardService boardService;
    private final FileService fileService;


    @ApiOperation(value = "게시글 페이지네이션", notes = "게시글 페이지네이션")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageInfoDto", value = "현재 페이지", required = false, dataType = "PageInfoDto", paramType = "query"),
            @ApiImplicitParam(name = "request", value = "HttpServletRequest 객체", dataType = "HttpServletRequest", paramType = "query")
    })
    @NoAuth
    @GetMapping
    public ResponseEntity<Map<String, Object>> getList(PageInfoDto pageInfoDto, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (pageInfoDto.getPage() == 0) {
            pageInfoDto = new PageInfoDto(1, 10);
        }
        log.info("pageInfoDto : {}", pageInfoDto);
        PageHelper.startPage(pageInfoDto.getPage(), pageInfoDto.getPageSize());
        Page<Board> boards = boardService.getBoardList();
        String path = request.getContextPath() + "/board/list?page";
        PageNavigationForPageHelper helper = new PageNavigationForPageHelper(boards, path);
        map.put("boards", helper);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 페이지네이션", notes = "게시글 페이지네이션")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "현재 페이지", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "페이지 크기", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "request", value = "HttpServletRequest 객체", required = true, dataType = "HttpServletRequest", paramType = "query")
    })
    @NoAuth
    @GetMapping("/list/{currentPage}")
    public ResponseEntity<Map<String, Object>> getListByPage(@PathVariable int currentPage, @RequestParam(required = false) Integer pageSize, HttpServletRequest request) {
        log.info("page By currentPage : {}", currentPage);
        Map<String, Object> map = new HashMap<>();
        if (pageSize == null) {
            pageSize = 10;
        }
        PageHelper.startPage(currentPage, pageSize);
        Page<Board> boards = boardService.getBoardList();
        String path = request.getContextPath() + "/board/list?page";
        PageNavigationForPageHelper helper = new PageNavigationForPageHelper(boards, path);
        map.put("boards", helper);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 검색", notes = "게시글 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageInfoDto", value = "페이지 정보", required = false, dataType = "PageInfoDto", paramType = "query"),
            @ApiImplicitParam(name = "searchDto", value = "검색 조건", required = true, dataType = "SearchDto", paramType = "body"),
            @ApiImplicitParam(name = "request", value = "HttpServletRequest 객체", required = false, dataType = "HttpServletRequest", paramType = "query")
    })
    @NoAuth
    @GetMapping("/list/search")
    public ResponseEntity<Map<String, Object>> getListBySearchDto(PageInfoDto pageInfoDto, @ModelAttribute SearchDto searchDto, HttpServletRequest request) {
        log.info("searchDto : {}", searchDto);
        Map<String, Object> map = new HashMap<>();
        if (pageInfoDto.getPage() == 0) {
            pageInfoDto = new PageInfoDto(1, 10);
        }
        PageHelper.startPage(pageInfoDto.getPage(), pageInfoDto.getPageSize());
        Page<Board> boards = boardService.getBoardListBySearchDto(searchDto);
        String path = request.getContextPath() + "/board/list/search?page";
        PageNavigationForPageHelper helper = new PageNavigationForPageHelper(boards, path);
        map.put("boards", helper);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 상세 조회")
    @ApiImplicitParam(name = "boardId", value = "게시글 번호", required = true, dataType = "int", paramType = "path")
    @NoAuth
    @GetMapping("/{boardId}")
    public ApiResult<BoardResponseDto> getBoard(@PathVariable("boardId") int boardId) {
        log.info("boardId : {}", boardId);
        return success(new BoardResponseDto(boardService.detail(boardId)));
    }

    @ApiOperation(value = "게시글 등록", notes = "게시글을 등록한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "게시글 등록 정보", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "files", value = "게시글 등록 파일", required = false, dataType = "List<MultipartFile>", paramType = "query")
    })
    @PostMapping
    public ApiResult<Boolean> registerBoard(@RequestParam @Valid String json, List<MultipartFile> files) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BoardRequestDto boardRequestDto = objectMapper.readValue(json, BoardRequestDto.class);
        log.info("boardRequestDto : {}", boardRequestDto);
        int pk = boardService.regist(boardRequestDto, boardRequestDto.getUserId());
        log.info(pk + "번 게시글에 파일 업로드");
        if (files != null) {
            fileService.insertFile(pk, files, "board/");
        }
        return success(true);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "boardRequestDto", value = "게시글 수정 정보", required = true, dataType = "BoardRequestDto", paramType = "body"),
            @ApiImplicitParam(name = "userId", value = "사용자 아이디", required = true, dataType = "String", paramType = "query")
    })
    @PutMapping("/{boardId}")
    public ApiResult<Boolean> modifyBoard(@PathVariable int boardId, @RequestBody @Valid BoardRequestDto boardRequestDto, @RequestParam String userId) {
        log.info("boardId : {}", boardId);
        boardService.modify(boardId, userId, boardRequestDto);
        return success(true);
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "사용자 아이디", required = true, dataType = "String", paramType = "query")
    })
    @DeleteMapping("/{boardId}")
    public ApiResult<Boolean> deleteBoard(@PathVariable int boardId, @RequestParam String userId) {
        boardService.delete(boardId, userId);
        return success(true);
    }

    @ApiOperation(value = "게시글 조회수 증가", notes = "게시글 조회수를 증가시킨다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", required = true, dataType = "int", paramType = "path")
    })
    @NoAuth
    @PostMapping("/hit/{boardId}")
    public ApiResult<Boolean> updateHit(@PathVariable int boardId) {
        boardService.updateHit(boardId);
        return success(true);
    }

    @ApiOperation(value = "게시글 파일 조회", notes = "게시글 파일을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시글 번호", required = true, dataType = "int", paramType = "path")
    })
    @NoAuth
    @GetMapping("/file/{boardId}")
    public ApiResult<List<FileInfo>> get(@PathVariable int boardId) {
        List<FileInfo> list = fileService.selectFile(boardId);
        log.info("list : {}", list);
        return success(list);
    }
}

package com.ssafy.enjoytrip.board.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.enjoytrip.board.dao.BoardRepository;
import com.ssafy.enjoytrip.board.dao.CommentRepository;
import com.ssafy.enjoytrip.board.model.dto.request.BoardModifyRequest;
import com.ssafy.enjoytrip.board.model.dto.request.BoardSaveRequest;
import com.ssafy.enjoytrip.board.model.dto.request.PageInfoRequest;
import com.ssafy.enjoytrip.board.model.dto.request.SearchDto;
import com.ssafy.enjoytrip.board.model.dto.response.PageResponse;
import com.ssafy.enjoytrip.board.model.entity.Board;
import com.ssafy.enjoytrip.board.model.entity.Comment;
import com.ssafy.enjoytrip.board.util.PageNavigationForPageHelper;
import com.ssafy.enjoytrip.global.error.BoardException;
import com.ssafy.enjoytrip.global.util.JsonUtil;
import com.ssafy.enjoytrip.media.model.entity.FileInfo;
import com.ssafy.enjoytrip.media.service.FileService;
import com.ssafy.enjoytrip.user.model.dao.UserRepository;
import com.ssafy.enjoytrip.user.model.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final FileService fileService;

    @Override
    @Transactional
    public Long saveBoard(final String json, final List<MultipartFile> files, final String userId) {
        final User user = userRepository.selectByUserId(userId)
            .orElseThrow(() -> new BoardException("해당 userId에 해당하는 user가 없습니다."));

        final BoardSaveRequest request = (BoardSaveRequest)
            JsonUtil.readValue(json, BoardSaveRequest.class);

        final Board board = Board.builder()
            .boardType(request.getBoardType())
            .subject(request.getSubject())
            .content(request.getContent())
            .userId(user.getUserId())
            .build();

        final Long boardId = boardRepository.insertBoard(board);
        fileService.insertFile(boardId, files, "board/");

        return boardId;
    }

    @Override
    public PageResponse getBoardList(PageInfoRequest pageInfoRequest, String path) {
        if (pageInfoRequest.getPage() == 0) {
            pageInfoRequest = new PageInfoRequest(1, 10);
        }

        PageHelper.startPage(pageInfoRequest.getPage(), pageInfoRequest.getPageSize());
        return new PageResponse(
            new PageNavigationForPageHelper(boardRepository.selectAll(), path));
    }

    @Override
    public PageResponse getListByPage(
        final PageInfoRequest pageInfoRequest,
        final String path
    ) {
        return getBoardList(pageInfoRequest, path);
    }

    @Override
    public PageResponse getBoardListBySearchDto(
        final SearchDto searchDto,
        PageInfoRequest pageInfoRequest,
        final String path
    ) {
        if (pageInfoRequest.getPage() == 0) {
            pageInfoRequest = new PageInfoRequest(1, 10);
        }
        PageHelper.startPage(pageInfoRequest.getPage(), pageInfoRequest.getPageSize());

        final Page<Board> boards = boardRepository.selectBoardListBySearchDto(searchDto);
        return PageResponse.from(new PageNavigationForPageHelper(boards, path));
    }


    @Override
    public Board detail(final Long boardId) {
        final Board board = boardRepository
            .selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 board가 없습니다."));

        // TODO : 한번에 조인해오기
        final List<FileInfo> fileInfos = fileService.selectFile(boardId);
        final List<Comment> comments = commentRepository.selectAll(boardId);
        board.setFileInfos(fileInfos);
        board.setComments(comments);

        return board;
    }

    @Override
    @Transactional
    public void modify(
        final Long boardId,
        final String userId,
        final BoardModifyRequest boardModifyRequest
    ) {
        final User user = userRepository
            .selectByUserId(userId)
            .orElseThrow(() -> new BoardException("해당 유저가 없습니다."));

        final Board board = boardRepository
            .selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 board가 없습니다."));

        validateSameMember(userId, board.getUserId());

        final Board modifyBoard = Board.builder()
            .boardId(boardId)
            .userId(user.getUserId())
            .subject(boardModifyRequest.getSubject())
            .content(boardModifyRequest.getContent())
            .build();

        boardRepository.updateBoard(modifyBoard);
    }

    private void validateSameMember(final String userId, final String boardUserId) {
        if (!userId.equals(boardUserId)) {
            throw new BoardException("해당 유저가 아닙니다.");
        }
    }

    @Override
    @Transactional
    public void delete(final Long boardId, final String userId) {
        final User user = userRepository.selectByUserId(userId)
            .orElseThrow(() -> new BoardException("해당 유저가 없습니다."));
        final Board board = boardRepository.selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 Board가 없습니다."));

        validateSameMember(user.getUserId(), board.getUserId());

        // TODO 사진들 다 삭제 + 댓글들 다 삭제
        commentRepository.deleteAll(boardId);
        boardRepository.deleteBoard(boardId);
        fileService.deleteFile(boardId);
    }

    @Override
    @Transactional
    public void updateHit(final Long boardId) {
        boardRepository.updateHit(boardId);
    }
}

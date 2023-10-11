package com.ssafy.enjoytrip.core.board.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.enjoytrip.core.board.dao.BoardRepository;
import com.ssafy.enjoytrip.core.board.dao.CommentRepository;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardModifyRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSaveRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSearchRequest;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardDetailResponse;
import com.ssafy.enjoytrip.core.board.model.entity.Board;
import com.ssafy.enjoytrip.core.board.model.entity.Comment;
import com.ssafy.enjoytrip.core.board.util.PageNavigationForPageHelper;
import com.ssafy.enjoytrip.core.media.model.dto.FileInfoResponse;
import com.ssafy.enjoytrip.core.media.model.entity.FileInfo;
import com.ssafy.enjoytrip.core.media.service.FileService;
import com.ssafy.enjoytrip.core.media.service.MediaService;
import com.ssafy.enjoytrip.core.media.service.UploadService;
import com.ssafy.enjoytrip.core.user.dao.UserRepository;
import com.ssafy.enjoytrip.core.user.model.entity.User;
import com.ssafy.enjoytrip.global.dto.PageInfoRequest;
import com.ssafy.enjoytrip.global.dto.PageResponse;
import com.ssafy.enjoytrip.global.error.BoardException;
import com.ssafy.enjoytrip.global.util.JsonUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BoardTransactionService boardTransactionService;

    private final MediaService mediaService;
    private final FileService fileService;
    private final UploadService uploadService;

    @Override
    public Long saveBoard(final String json, final List<MultipartFile> files, final String userId) {
        final User user = findUserByUserId(userId);
        final Board board = getBoard(json, user.getUserId());
        boardRepository.insertBoard(board);

        if (files != null) {
            saveImages(files, user.getUserId(), board.getBoardId());
        }
        return board.getBoardId();
    }

    private void saveImages(
        final List<MultipartFile> files,
        final String userId,
        final Long boardId
    ) {
        try {
            mediaService.insertMedias(boardId, files, "board/" + userId);
        } catch (final Exception e) {
            boardRepository.deleteBoard(boardId);
            throw new BoardException("파일 저장에 실패하였습니다.");
        }
    }

    private Board getBoard(final String json, final String userId) {
        final BoardSaveRequest request = (BoardSaveRequest)
            JsonUtil.readValue(json, BoardSaveRequest.class);

        return Board.builder()
            .boardType(request.getBoardType())
            .subject(request.getSubject())
            .content(request.getContent())
            .userId(userId)
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse getBoardList(final PageInfoRequest pageInfoRequest) {
        PageHelper.startPage(pageInfoRequest.getPage(), pageInfoRequest.getPageSize());

        return PageResponse.from(
            new PageNavigationForPageHelper(boardRepository.selectAll(), "/board/list?page"));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse getBoardListBySearchDto(
        final BoardSearchRequest boardSearchRequest, final PageInfoRequest pageInfoRequest
    ) {
        PageHelper.startPage(pageInfoRequest.getPage(), pageInfoRequest.getPageSize());
        final Page<Board> boards = boardRepository.selectBoardListBySearchDto(boardSearchRequest);

        return PageResponse.from(
            new PageNavigationForPageHelper(boards, "/board/list/search?page"));
    }


    @Override
    @Transactional(readOnly = true)
    public BoardDetailResponse detail(final Long boardId) {
        final Board board = findBoardByBoardId(boardId);
        final List<FileInfo> fileInfos = fileService.selectFile(boardId).stream()
            .map(FileInfoResponse::toEntity)
            .collect(Collectors.toList());
        final List<Comment> comments = commentRepository.selectAll(boardId);

        return BoardDetailResponse.of(board, comments, fileInfos);
    }

    @Override
    @Transactional
    public void modify(
        final Long boardId, final String userId,
        final String json, final List<MultipartFile> files
    ) {
        final BoardModifyRequest boardModifyRequest =
            (BoardModifyRequest) JsonUtil.readValue(json, BoardModifyRequest.class);

        final User user = findUserByUserId(userId);
        final Board board = findBoardByBoardId(boardId);

        validateSameMember(userId, board.getUserId());

        final Board modifyBoard = Board.builder()
            .boardId(boardId)
            .userId(user.getUserId())
            .subject(boardModifyRequest.getSubject())
            .content(boardModifyRequest.getContent())
            .build();

        boardTransactionService.updateBoard(modifyBoard);
        if (files != null) {
            modifyMedias(board, files);
        }
    }

    private void modifyMedias(final Board board, final List<MultipartFile> files) {
        try {
            uploadService.deleteMedias(findFileUrls(board.getBoardId()));
            mediaService.insertMedias(board.getBoardId(), files, "board/" + board.getUserId());

        } catch (final Exception e) {
            boardTransactionService.updateBoard(board);
            throw new BoardException("게시글 수정에 실패하였습니다.");
        }
    }

    @Override
    public void delete(final Long boardId, final String userId) {
        final User user = findUserByUserId(userId);
        final Board board = findBoardByBoardId(boardId);

        validateSameMember(user.getUserId(), board.getUserId());

        boardTransactionService.deleteBoard(boardId);
        deleteMedias(board);
    }

    private void deleteMedias(final Board board) {
        try {
            final List<String> fileUrls = findFileUrls(board.getBoardId());
            uploadService.deleteMedias(fileUrls);
        } catch (final Exception e) {
            boardTransactionService.insertBoard(board);
            throw new BoardException("게시글 삭제에 실패하였습니다.");
        }
    }

    @Override
    @Transactional
    public void updateHit(final Long boardId) {
        boardRepository.updateHit(boardId);
    }

    private List<String> findFileUrls(final Long boardId) {
        return fileService.selectFile(boardId).stream()
            .map(FileInfoResponse::getFileUrl)
            .collect(Collectors.toList());
    }

    private void validateSameMember(final String userId, final String boardUserId) {
        if (!userId.equals(boardUserId)) {
            throw new BoardException("해당 유저가 아닙니다.");
        }
    }

    private Board findBoardByBoardId(final Long boardId) {
        return boardRepository.selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 Board가 없습니다."));
    }

    private User findUserByUserId(final String userId) {
        return userRepository.selectByUserId(userId)
            .orElseThrow(() -> new BoardException("해당 userId에 해당하는 user가 없습니다."));
    }
}

package com.ssafy.enjoytrip.core.board.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.enjoytrip.core.board.dao.BoardRepository;
import com.ssafy.enjoytrip.core.board.dao.CommentRepository;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardModifyRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSaveRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.PageInfoRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.SearchDto;
import com.ssafy.enjoytrip.core.board.model.dto.response.PageResponse;
import com.ssafy.enjoytrip.core.board.model.entity.Board;
import com.ssafy.enjoytrip.core.board.model.entity.Comment;
import com.ssafy.enjoytrip.core.board.util.PageNavigationForPageHelper;
import com.ssafy.enjoytrip.core.media.model.dto.FileInfoResponse;
import com.ssafy.enjoytrip.core.media.model.entity.FileInfo;
import com.ssafy.enjoytrip.core.media.service.FileService;
import com.ssafy.enjoytrip.core.media.service.MediaService;
import com.ssafy.enjoytrip.core.user.dao.UserRepository;
import com.ssafy.enjoytrip.core.user.model.entity.User;
import com.ssafy.enjoytrip.global.error.BoardException;
import com.ssafy.enjoytrip.global.util.JsonUtil;
import com.ssafy.enjoytrip.infra.S3Service;
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
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final MediaService mediaService;
    private final FileService fileService;
    private final S3Service s3Service;

    @Override
    public Long saveBoard(final String json, final List<MultipartFile> files, final String userId) {
        final User user = userRepository.selectByUserId(userId)
            .orElseThrow(() -> new BoardException("해당 userId에 해당하는 user가 없습니다."));
        final Long boardId = boardRepository.insertBoard(getBoard(json, user.getUserId()));

        return saveImages(files, user.getUserId(), boardId);
    }

    private Long saveImages(
        final List<MultipartFile> files,
        final String userId,
        final Long boardId
    ) {
        try {
            mediaService.insertMedias(boardId, files, "board/" + userId);
            return boardId;

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
    public PageResponse getBoardList(final PageInfoRequest pageInfoRequest) {
        PageHelper.startPage(pageInfoRequest.getPage(), pageInfoRequest.getPageSize());
        return new PageResponse(
            new PageNavigationForPageHelper(boardRepository.selectAll(), "/board/list?page"));
    }

    @Override
    public PageResponse getBoardListBySearchDto(
        final SearchDto searchDto, final PageInfoRequest pageInfoRequest) {

        PageHelper.startPage(pageInfoRequest.getPage(), pageInfoRequest.getPageSize());
        final Page<Board> boards = boardRepository.selectBoardListBySearchDto(searchDto);
        return PageResponse.from(
            new PageNavigationForPageHelper(boards, "/board/list/search?page"));
    }


    @Override
    public Board detail(final Long boardId) {
        final Board board = boardRepository
            .selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 board가 없습니다."));

        // TODO : 한번에 조인해오기
        List<FileInfoResponse> fileInfoResponses = fileService.selectFile(boardId);
        List<FileInfo> fileInfos = fileInfoResponses.stream()
            .map(FileInfoResponse::toEntity)
            .collect(Collectors.toList());

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

        // TODO : 이미지 업로드 까지 수정
        boardRepository.updateBoard(modifyBoard);
    }

    private void validateSameMember(final String userId, final String boardUserId) {
        if (!userId.equals(boardUserId)) {
            throw new BoardException("해당 유저가 아닙니다.");
        }
    }

    @Override
    public void delete(final Long boardId, final String userId) {
        final User user = userRepository.selectByUserId(userId)
            .orElseThrow(() -> new BoardException("해당 유저가 없습니다."));
        final Board board = boardRepository.selectBoard(boardId)
            .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 Board가 없습니다."));
        validateSameMember(user.getUserId(), board.getUserId());

        //  TODO : S3 지우기 + transaction 분리
        final List<String> fileInfos = fileService.selectFile(boardId).stream()
            .map(FileInfoResponse::getFileUrl).collect(Collectors.toList());

        s3Service.deleteMedias(fileInfos);

        fileService.deleteFile(boardId);
        commentRepository.deleteAll(boardId);
        boardRepository.deleteBoard(boardId);
    }

    @Override
    @Transactional
    public void updateHit(final Long boardId) {
        boardRepository.updateHit(boardId);
    }
}

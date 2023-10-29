package com.ssafy.enjoytrip.core.board.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssafy.enjoytrip.core.board.dao.BoardImageRepository;
import com.ssafy.enjoytrip.core.board.dao.BoardRepository;
import com.ssafy.enjoytrip.core.board.dao.CommentRepository;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardModifyRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSaveRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSearchRequest;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardDetailResponse;
import com.ssafy.enjoytrip.core.board.model.entity.Board;
import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import com.ssafy.enjoytrip.core.board.model.entity.Comment;
import com.ssafy.enjoytrip.core.user.dao.UserRepository;
import com.ssafy.enjoytrip.core.user.model.entity.User;
import com.ssafy.enjoytrip.global.dto.PageResponse;
import com.ssafy.enjoytrip.global.error.BoardException;
import com.ssafy.enjoytrip.infra.PageNavigationForPageHelper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long saveBoard(final BoardSaveRequest request, final String userId) {
        final User user = findUserByUserId(userId);

        final Board board =
                Board.builder()
                        .boardType(request.getBoardType())
                        .subject(request.getSubject())
                        .content(request.getContent())
                        .userId(user.getUserId())
                        .imageUrls(request.getFileUrls())
                        .build();

        boardRepository.insertBoard(board);
        if (board.isImageUrlNotEmpty()) {
            insertImageUrls(request.getFileUrls(), board.getBoardId());
        }

        return board.getBoardId();
    }

    private void insertImageUrls(final List<String> imageUrls, final Long boardId) {
        final List<BoardImageInfo> boardImageInfos =
                imageUrls.stream()
                        .map(imageUrl -> BoardImageInfo.of(boardId, imageUrl))
                        .collect(Collectors.toList());

        boardImageRepository.insertFile(boardId, boardImageInfos);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse getBoardList(final BoardSearchRequest boardSearchRequest) {
        PageHelper.startPage(boardSearchRequest.getPage(), boardSearchRequest.getPageSize());
        final Page<Board> boards = boardRepository.selectBoardList(boardSearchRequest);

        return PageResponse.from(new PageNavigationForPageHelper(boards, "/board/"));
    }

    @Override
    @Transactional(readOnly = true)
    public BoardDetailResponse detail(final Long boardId) {
        final Board board = findBoardByBoardId(boardId);

        final List<BoardImageInfo> boardImageInfos =
                boardImageRepository.selectFileByBoardId(boardId).stream()
                        .collect(Collectors.toList());

        final List<Comment> comments = commentRepository.selectAll(boardId);

        return BoardDetailResponse.of(board, comments, boardImageInfos);
    }

    @Override
    @Transactional
    public void modify(
            final Long boardId, final String userId, final BoardModifyRequest boardModifyRequest) {
        final User user = findUserByUserId(userId);
        final Board board = findBoardByBoardId(boardId);

        validateSameMember(userId, board.getUserId());

        final Board modifyBoard =
                Board.builder()
                        .boardId(boardId)
                        .userId(user.getUserId())
                        .subject(boardModifyRequest.getSubject())
                        .content(boardModifyRequest.getContent())
                        .imageUrls(boardModifyRequest.getImageUrls())
                        .build();

        boardRepository.updateBoard(modifyBoard);

        if (modifyBoard.isImageUrlNotEmpty()) {
            boardImageRepository.deleteFileByBoardId(boardId);
            insertImageUrls(boardModifyRequest.getImageUrls(), board.getBoardId());
        }
    }

    @Override
    @Transactional
    public void delete(final Long boardId, final String userId) {
        final User user = findUserByUserId(userId);
        final Board board = findBoardByBoardId(boardId);

        validateSameMember(user.getUserId(), board.getUserId());

        boardImageRepository.deleteFileByBoardId(boardId);
        commentRepository.deleteAll(boardId);
        boardRepository.deleteBoard(boardId);
    }

    @Override
    @Transactional
    public void updateHit(final Long boardId) {
        boardRepository.updateHit(boardId);
    }

    private void validateSameMember(final String userId, final String boardUserId) {
        if (!userId.equals(boardUserId)) {
            throw new BoardException("해당 유저가 아닙니다.");
        }
    }

    private Board findBoardByBoardId(final Long boardId) {
        return boardRepository
                .selectBoard(boardId)
                .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 Board가 없습니다."));
    }

    private User findUserByUserId(final String userId) {
        return userRepository
                .selectByUserId(userId)
                .orElseThrow(() -> new BoardException("해당 userId에 해당하는 user가 없습니다."));
    }
}

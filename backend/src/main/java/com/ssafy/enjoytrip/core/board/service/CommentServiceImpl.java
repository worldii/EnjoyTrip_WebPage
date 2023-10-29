package com.ssafy.enjoytrip.core.board.service;


import com.ssafy.enjoytrip.core.board.dao.BoardRepository;
import com.ssafy.enjoytrip.core.board.dao.CommentRepository;
import com.ssafy.enjoytrip.core.board.model.dto.request.CommentModifyRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.CommentSaveRequest;
import com.ssafy.enjoytrip.core.board.model.dto.response.CommentResponse;
import com.ssafy.enjoytrip.core.board.model.entity.Board;
import com.ssafy.enjoytrip.core.board.model.entity.Comment;
import com.ssafy.enjoytrip.core.user.dao.UserRepository;
import com.ssafy.enjoytrip.core.user.model.entity.User;
import com.ssafy.enjoytrip.global.error.BoardException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long save(
            final CommentSaveRequest commentSaveRequest, final String userId, final Long boardId) {
        final Board board = findByBoardId(boardId);
        final User user = findByUserId(userId);

        final Comment comment =
                Comment.builder()
                        .content(commentSaveRequest.getContent())
                        .boardId(board.getBoardId())
                        .userId(user.getUserId())
                        .build();

        commentRepository.insertComment(comment);
        return comment.getCommentId();
    }

    @Override
    public List<CommentResponse> getCommentList(final Long boardId) {
        final Board board = findByBoardId(boardId);

        return commentRepository.selectAll(board.getBoardId()).stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public Comment detail(final Long commentId) {
        return findByCommentId(commentId);
    }

    @Override
    @Transactional
    public void modify(
            final Long commentId, final String userId, final CommentModifyRequest request) {
        final Board board = findByBoardId(request.getBoardId());
        final User user = findByUserId(userId);
        final Comment comment = findByCommentId(commentId);

        validateSameUser(comment.getUserId(), user.getUserId());

        final Comment newComment =
                Comment.builder()
                        .commentId(comment.getCommentId())
                        .content(request.getContent())
                        .boardId(board.getBoardId())
                        .userId(user.getUserId())
                        .build();

        commentRepository.updateComment(newComment);
    }

    @Override
    @Transactional
    public void deleteAll(Long boardId) {
        final Board board = findByBoardId(boardId);

        commentRepository.deleteAll(board.getBoardId());
    }

    @Override
    @Transactional
    public void delete(final Long commentId, final String userId) {
        final Comment comment = findByCommentId(commentId);
        final User user = findByUserId(userId);

        validateSameUser(comment.getUserId(), user.getUserId());

        commentRepository.deleteComment(commentId);
    }

    private Comment findByCommentId(final Long commentId) {
        return commentRepository
                .selectComment(commentId)
                .orElseThrow(() -> new BoardException("해당 commentId에 해당하는 comment가 없습니다."));
    }

    private Board findByBoardId(final Long boardId) {
        return boardRepository
                .selectBoard(boardId)
                .orElseThrow(() -> new BoardException("해당 boardId에 해당하는 board가 없습니다."));
    }

    private User findByUserId(final String userId) {
        return userRepository
                .selectByUserId(userId)
                .orElseThrow(() -> new BoardException("해당 userId에 해당하는 user가 없습니다."));
    }

    private void validateSameUser(final String commentUserId, final String userId) {
        if (!commentUserId.equals(userId)) {
            throw new BoardException("유저 아이디가 다릅니다.");
        }
    }
}

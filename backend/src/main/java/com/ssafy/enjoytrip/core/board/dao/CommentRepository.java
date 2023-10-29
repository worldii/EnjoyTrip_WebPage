package com.ssafy.enjoytrip.core.board.dao;


import com.ssafy.enjoytrip.core.board.model.entity.Comment;
import com.ssafy.enjoytrip.core.board.model.mapper.CommentMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentRepository {

    private final CommentMapper commentMapper;

    public Optional<Comment> selectComment(final Long commentId) {
        return commentMapper.selectComment(commentId);
    }

    @Transactional
    public Long insertComment(final Comment comment) {
        return commentMapper.insertComment(comment);
    }

    @Transactional
    public void updateComment(final Comment comment) {
        commentMapper.updateComment(comment);
    }

    @Transactional
    public void deleteComment(final Long commentId) {
        commentMapper.deleteComment(commentId);
    }

    @Transactional
    public void deleteAll(final Long boardId) {
        commentMapper.deleteAll(boardId);
    }

    public List<Comment> selectAll(final Long boardId) {
        return commentMapper.selectAll(boardId);
    }
}

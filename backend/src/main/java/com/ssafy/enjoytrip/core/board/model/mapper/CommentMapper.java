package com.ssafy.enjoytrip.core.board.model.mapper;

import com.ssafy.enjoytrip.core.board.model.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    Optional<Comment> selectComment(final Long commentId);

    Long insertComment(final Comment comment);

    void updateComment(final Comment comment);

    void deleteComment(final Long commentId);

    void deleteAll(final Long boardId);

    List<Comment> selectAll(final Long boardId);
}

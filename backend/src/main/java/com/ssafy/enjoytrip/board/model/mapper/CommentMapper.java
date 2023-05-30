package com.ssafy.enjoytrip.board.model.mapper;

import com.github.pagehelper.Page;
import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    Optional<Comment> selectComment(int commentId);
    int insertComment(Comment comment);
    int updateComment(Comment comment);
    int deleteComment(int commentId);
    List<Comment> selectAll(int boardId);

}

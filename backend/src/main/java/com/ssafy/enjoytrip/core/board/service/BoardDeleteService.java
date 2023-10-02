package com.ssafy.enjoytrip.core.board.service;

import com.ssafy.enjoytrip.core.board.dao.BoardRepository;
import com.ssafy.enjoytrip.core.board.dao.CommentRepository;
import com.ssafy.enjoytrip.core.media.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BoardDeleteService {

    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final CommentRepository commentRepository;

    @Transactional
    public void deleteBoard(Long boardId) {
        fileService.deleteFile(boardId);
        commentRepository.deleteAll(boardId);
        boardRepository.deleteBoard(boardId);
    }

    @Transactional
    public void restoreBoard(Long boardId) {
        // TODO :
        return;
    }


}

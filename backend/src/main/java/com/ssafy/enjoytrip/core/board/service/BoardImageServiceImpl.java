package com.ssafy.enjoytrip.core.board.service;

import com.ssafy.enjoytrip.core.board.dao.BoardImageRepository;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardImageInfoResponse;
import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardImageServiceImpl implements BoardImageService {

    private final BoardImageRepository boardImageRepository;

    @Override
    @Transactional
    public void insertBoardImage(final Long boardId, final List<String> imageUrls) {
        final List<BoardImageInfo> boardImageInfos = imageUrls.stream()
            .map(imageUrl -> BoardImageInfo.of(boardId, imageUrl))
            .collect(Collectors.toList());

        boardImageRepository.insertFile(boardId, boardImageInfos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardImageInfoResponse> selectBoardImage(final Long boardId) {
        return boardImageRepository.selectFileByBoardId(boardId).stream()
            .map(BoardImageInfoResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteBoardImage(final Long boardId) {
        boardImageRepository.deleteFileByBoardId(boardId);
    }

    @Override
    @Transactional
    public void modifyBoardImage(final Long boardId, final List<String> fileUrls) {
        boardImageRepository.deleteFileByBoardId(boardId);
        insertBoardImage(boardId, fileUrls);
    }
}

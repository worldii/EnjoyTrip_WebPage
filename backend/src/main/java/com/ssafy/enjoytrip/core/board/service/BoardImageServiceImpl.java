package com.ssafy.enjoytrip.core.board.service;

import com.ssafy.enjoytrip.core.board.dao.boardImageRepository;
import com.ssafy.enjoytrip.core.board.model.dto.request.BoardImageInfoResponse;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardImageUrlResponse;
import com.ssafy.enjoytrip.core.board.model.entity.BoardImageInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardImageServiceImpl implements BoardImageService {

    private final boardImageRepository boardImageRepository;

    @Override
    @Transactional
    public void insertFile(
        final Long boardId,
        final List<String> fileUrls
    ) {
        final List<BoardImageInfo> boardImageInfos = fileUrls.stream()
            .map(fileUrl -> BoardImageInfo.of(boardId, fileUrl))
            .collect(Collectors.toList());

        boardImageRepository.insertFile(boardId, boardImageInfos);
    }

    @Override
    public List<BoardImageInfoResponse> selectFile(final Long boardId) {
        return boardImageRepository.selectFileByBoardId(boardId).stream()
            .map(BoardImageInfoResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteFile(final Long boardId) {
        boardImageRepository.deleteFileByBoardId(boardId);
    }

    @Override
    @Transactional
    public void modifyFile(Long boardId, List<String> fileUrls) {
        boardImageRepository.deleteFileByBoardId(boardId);
        insertFile(boardId, fileUrls);
    }

    @Override
    public void deleteMedias(Long boardId) {

    }

    @Override
    public List<BoardImageUrlResponse> uploadMedias(List<MultipartFile> files, String s) {
        return null;
    }
}

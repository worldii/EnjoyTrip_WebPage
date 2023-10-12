package com.ssafy.enjoytrip.core.board.service;

import com.ssafy.enjoytrip.core.board.model.dto.request.BoardImageInfoResponse;
import java.util.List;


public interface BoardImageService {

    void insertBoardImage(final Long boardId, final List<String> imageUrls);

    List<BoardImageInfoResponse> selectBoardImage(final Long boardId);

    void deleteBoardImage(final Long boardId);

    void modifyBoardImage(final Long boardId, final List<String> imageUrls);
}



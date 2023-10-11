package com.ssafy.enjoytrip.core.board.service;

import com.ssafy.enjoytrip.core.board.model.dto.request.BoardSearchRequest;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardDetailResponse;
import com.ssafy.enjoytrip.global.dto.PageResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

    Long saveBoard(final String json, List<MultipartFile> files, final String userId);

    BoardDetailResponse detail(final Long boardId);

    PageResponse getBoardList(final BoardSearchRequest boardSearchRequest);

    void modify(
        final Long boardId,
        final String userId,
        final String request,
        final List<MultipartFile> files
    );

    void updateHit(final Long boardId);

    void delete(final Long boardId, final String userId);
}

package com.ssafy.enjoytrip.core.board.service;

import com.ssafy.enjoytrip.core.board.model.dto.request.BoardModifyRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.PageInfoRequest;
import com.ssafy.enjoytrip.core.board.model.dto.request.SearchDto;
import com.ssafy.enjoytrip.core.board.model.dto.response.PageResponse;
import com.ssafy.enjoytrip.core.board.model.entity.Board;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

    Long saveBoard(final String json, List<MultipartFile> files, final String userId);

    Board detail(final Long boardId);

    PageResponse getBoardListBySearchDto(
        final SearchDto searchDto, final PageInfoRequest pageInfoRequest);

    PageResponse getBoardList(final PageInfoRequest pageInfoRequest);

    void modify(final Long boardId, final String userId, final BoardModifyRequest request);

    void updateHit(final Long boardId);

    void delete(final Long boardId, final String userId);
}

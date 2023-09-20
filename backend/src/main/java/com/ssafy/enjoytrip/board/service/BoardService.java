package com.ssafy.enjoytrip.board.service;

import com.ssafy.enjoytrip.board.model.dto.Board;
import com.ssafy.enjoytrip.board.model.dto.request.BoardModifyRequest;
import com.ssafy.enjoytrip.board.model.dto.request.PageInfoRequest;
import com.ssafy.enjoytrip.board.model.dto.request.SearchDto;
import com.ssafy.enjoytrip.board.model.dto.response.PageResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

    Long saveBoard(final String json, List<MultipartFile> files, final String userId);

    Board detail(final Long boardId);

    PageResponse getBoardListBySearchDto(
        final SearchDto searchDto, final PageInfoRequest pageInfoRequest,
        final String path);

    PageResponse getBoardList(final PageInfoRequest pageInfoRequest, final String path);

    PageResponse getListByPage(final PageInfoRequest pageInfoRequest, final String path);


    void modify(final Long boardId, final String userId, final BoardModifyRequest request);

    void delete(final Long boardId, final String userId);

    void updateHit(final Long boardId);
}

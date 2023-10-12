package com.ssafy.enjoytrip.core.board.service;

import com.ssafy.enjoytrip.core.board.model.dto.request.BoardImageInfoResponse;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardImageUrlResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public interface BoardImageService {

    void insertFile(final Long boardId, final List<String> imageUrls);

    List<BoardImageInfoResponse> selectFile(final Long boardId);

    void deleteFile(final Long boardId);

    void modifyFile(final Long boardId, final List<String> imageUrls);

    void deleteMedias(Long boardId);

    List<BoardImageUrlResponse> uploadMedias(List<MultipartFile> files, String s);
}



package com.ssafy.enjoytrip.core.media.service;

import com.ssafy.enjoytrip.core.board.model.dto.response.BoardImageUrlResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    List<BoardImageUrlResponse> uploadMedias(
        final List<MultipartFile> multipartFiles,
        final String folderName);

    void deleteMedias(final List<String> fileUrls);
}

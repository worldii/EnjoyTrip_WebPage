package com.ssafy.enjoytrip.core.media.service;

import com.ssafy.enjoytrip.core.media.model.FileUrlResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    List<FileUrlResponse> uploadMedias(
        final List<MultipartFile> multipartFiles,
        final String folderName);

    void deleteMedias(final List<String> fileUrls);
}

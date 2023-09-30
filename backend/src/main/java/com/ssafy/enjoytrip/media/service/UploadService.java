package com.ssafy.enjoytrip.media.service;

import com.ssafy.enjoytrip.media.model.FileUrlResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    List<FileUrlResponse> uploadMedias(
        final List<MultipartFile> multipartFiles,
        final String folderName);

    void deleteMedias(final List<String> fileUrls);
}

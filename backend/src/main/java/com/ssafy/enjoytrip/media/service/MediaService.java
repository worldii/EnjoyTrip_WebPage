package com.ssafy.enjoytrip.media.service;

import com.ssafy.enjoytrip.global.error.MediaException;
import com.ssafy.enjoytrip.media.model.FileUrlResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final FileService fileService;
    private final S3Service s3Service;

    public void insertMedias(
        final Long boardId,
        final List<MultipartFile> imageFiles,
        final String folderName
    ) {
        final List<String> fileUrls = s3Service.uploadMediasToS3(imageFiles, folderName)
            .stream()
            .map(FileUrlResponse::getUrl)
            .collect(Collectors.toList());

        try {
            fileService.insertFile(boardId, fileUrls);
        } catch (final Exception e) {
            s3Service.deleteMediasFromS3(fileUrls);
            throw new MediaException("파일 업로드에 실패했습니다.");
        }
    }
}

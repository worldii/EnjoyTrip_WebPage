package com.ssafy.enjoytrip.core.media.service;

import com.ssafy.enjoytrip.core.board.model.dto.request.BoardImageInfoResponse;
import com.ssafy.enjoytrip.core.board.model.dto.response.BoardImageUrlResponse;
import com.ssafy.enjoytrip.core.board.service.BoardImageService;
import com.ssafy.enjoytrip.global.error.MediaException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final BoardImageService fileService;
    private final UploadService uploadService;

    public void insertMedias(
        final Long boardId,
        final List<MultipartFile> imageFiles,
        final String folderName
    ) {
        final List<String> fileUrls = getFileUrls(imageFiles, folderName);

        try {
            fileService.insertFile(boardId, fileUrls);
        } catch (final Exception e) {
            uploadService.deleteMedias(fileUrls);
            throw new MediaException("파일 업로드에 실패했습니다.");
        }
    }

    private List<String> getFileUrls(
        final List<MultipartFile> imageFiles,
        final String folderName
    ) {
        return uploadService.uploadMedias(imageFiles, folderName)
            .stream()
            .map(BoardImageUrlResponse::getUrl)
            .collect(Collectors.toList());
    }

    public void modifyMedias(
        final Long boardId,
        final List<MultipartFile> files,
        final String path
    ) {
        final List<String> fileUrls = getFileUrls(files, path);

        try {
            fileService.modifyFile(boardId, fileUrls);
        } catch (Exception e) {
            uploadService.deleteMedias(fileUrls);
            throw new MediaException("파일 수정에 실패했습니다.");
        }
    }

    public void deleteMedias(final Long boardId) {
        final List<String> fileUrls = fileService.selectFile(boardId).stream()
            .map(BoardImageInfoResponse::getImageUrl)
            .collect(Collectors.toList());

        uploadService.deleteMedias(fileUrls);
        fileService.deleteFile(boardId);
    }
}

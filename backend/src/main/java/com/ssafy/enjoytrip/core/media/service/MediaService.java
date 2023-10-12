package com.ssafy.enjoytrip.core.media.service;

import com.ssafy.enjoytrip.core.media.model.FileUrlResponse;
import com.ssafy.enjoytrip.core.media.model.dto.FileInfoResponse;
import com.ssafy.enjoytrip.global.error.BoardException;
import com.ssafy.enjoytrip.global.error.MediaException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final FileService fileService;
    private final UploadService uploadService;

    public void insertMedias(
        final Long boardId,
        final String userId,
        final List<MultipartFile> imageFiles,
        final String folderName
    ) {
        final List<String> fileUrls = getFileUrls(imageFiles, folderName);

        try {
            fileService.insertFile(boardId, userId, fileUrls);
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
            .map(FileUrlResponse::getUrl)
            .collect(Collectors.toList());
    }

    public void modifyMedias(
        final Long boardId,
        final String userId,
        final List<MultipartFile> files,
        final String path
    ) {
        final List<String> fileUrls = getFileUrls(files, path);

        try {
            fileService.modifyFile(boardId, userId, fileUrls);
        } catch (Exception e) {
            uploadService.deleteMedias(fileUrls);
            throw new MediaException("파일 수정에 실패했습니다.");
        }
    }

    public void deleteMedias(final Long boardId, final String userId) {
        final List<String> fileUrls = fileService.selectFile(boardId).stream()
            .map(FileInfoResponse::getFileUrl)
            .collect(Collectors.toList());

        fileService.selectFile(boardId).stream()
            .map(FileInfoResponse::getUserId)
            .forEach(boardUserId -> validateSameMember(userId, boardUserId));

        // FileService 에 userId 가지고 있어야 함
        uploadService.deleteMedias(fileUrls);
        fileService.deleteFile(boardId);
    }

    private void validateSameMember(final String userId, final String boardUserId) {
        if (!userId.equals(boardUserId)) {
            throw new BoardException("해당 유저가 아닙니다.");
        }
    }
}

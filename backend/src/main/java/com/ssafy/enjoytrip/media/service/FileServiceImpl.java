package com.ssafy.enjoytrip.media.service;

import com.ssafy.enjoytrip.media.dao.FileRepository;
import com.ssafy.enjoytrip.media.model.dto.FileInfoResponse;
import com.ssafy.enjoytrip.media.model.entity.FileInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public void insertFile(
        final Long boardId,
        final List<String> fileUrls
    ) {
        final List<FileInfo> fileInfos = fileUrls.stream()
            .map(fileUrl -> FileInfo.of(boardId, fileUrl))
            .collect(Collectors.toList());

        fileRepository.insertFile(boardId, fileInfos);
    }

    @Override
    public List<FileInfoResponse> selectFile(final Long boardId) {
        return fileRepository.selectFileByBoardId(boardId).stream()
            .map(FileInfoResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteFile(final Long boardId) {
        fileRepository.deleteFileByBoardId(boardId);
    }
}

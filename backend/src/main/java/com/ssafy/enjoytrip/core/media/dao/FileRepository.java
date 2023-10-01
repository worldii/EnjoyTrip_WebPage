package com.ssafy.enjoytrip.core.media.dao;

import com.ssafy.enjoytrip.core.media.model.entity.FileInfo;
import com.ssafy.enjoytrip.core.media.model.mapper.FileMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileRepository {

    private final FileMapper fileMapper;

    @Transactional
    public void insertFile(final Long boardId, final List<FileInfo> imageFiles) {
        fileMapper.insertFile(boardId, imageFiles);
    }

    public List<FileInfo> selectFileByBoardId(final Long boardId) {
        return fileMapper.selectFile(boardId);
    }

    @Transactional
    public void deleteFileByBoardId(final Long boardId) {
        fileMapper.deleteFileByBoardId(boardId);
    }
}

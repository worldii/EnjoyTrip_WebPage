package com.ssafy.enjoytrip.core.media.controller;

import com.ssafy.enjoytrip.core.media.model.FileUrlResponse;
import com.ssafy.enjoytrip.core.media.model.dto.FileInfoResponse;
import com.ssafy.enjoytrip.core.media.service.FileService;
import com.ssafy.enjoytrip.core.media.service.MediaService;
import com.ssafy.enjoytrip.core.media.service.UploadService;
import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;
    private final UploadService uploadService;
    private final FileService fileService;

    @NoAuth
    @GetMapping("/{boardId}")
    public ResponseEntity<List<FileInfoResponse>> getFileInfo(@PathVariable final Long boardId) {
        return ResponseEntity.ok(fileService.selectFile(boardId));
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<List<FileUrlResponse>> uploadImage(
        @PathVariable final Long boardId,
        @RequestParam("files") final List<MultipartFile> files,
        @RequestParam("folderName") final String folderName
    ) {
        return ResponseEntity.ok(uploadService.uploadMedias(files, folderName + boardId));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteImage(
        @PathVariable final Long boardId,
        @LoginUser final String userId
    ) {
        mediaService.deleteMedias(boardId, userId);
        return ResponseEntity.ok().build();
    }
}

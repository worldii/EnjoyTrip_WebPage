package com.ssafy.enjoytrip.core.board.controller;


import com.ssafy.enjoytrip.core.board.model.dto.response.BoardImageUrlResponse;
import com.ssafy.enjoytrip.core.board.service.BoardImageUploadService;
import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/board/media")
@RequiredArgsConstructor
public class BoardImageController {

    private final BoardImageUploadService imageUploadService;

    @PostMapping("/{boardId}")
    public ResponseEntity<List<BoardImageUrlResponse>> uploadImage(
            @PathVariable final Long boardId,
            @RequestPart("data") final List<MultipartFile> files,
            @LoginUser final String userId) {
        return ResponseEntity.ok(imageUploadService.uploadMedias(files, boardId, userId));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<List<BoardImageUrlResponse>> updateImage(
            @PathVariable final Long boardId,
            @RequestPart("data") final List<MultipartFile> files,
            @LoginUser final String userId) {
        return ResponseEntity.ok(imageUploadService.modifyMedias(files, boardId, userId));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable final Long boardId, @LoginUser final String userId) {
        imageUploadService.deleteMedias(boardId, userId);
        return ResponseEntity.noContent().build();
    }
}

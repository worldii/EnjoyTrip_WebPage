package com.ssafy.enjoytrip.core.board.controller;

import com.ssafy.enjoytrip.core.board.model.dto.request.BoardImageSaveRequest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/media")
@RequiredArgsConstructor
public class BoardImageController {

    private final BoardImageUploadService imageUploadService;

    @PostMapping("/{boardId}")
    public ResponseEntity<List<BoardImageUrlResponse>> uploadImage(
        @PathVariable final Long boardId,
        @RequestBody final BoardImageSaveRequest request,
        @LoginUser final String userId
    ) {
        return ResponseEntity.ok(
            imageUploadService.uploadMedias(request.getFiles(), boardId, userId));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<List<BoardImageUrlResponse>> updateImage(
        @PathVariable final Long boardId,
        @RequestBody final BoardImageSaveRequest request,
        @LoginUser final String userId
    ) {
        return ResponseEntity.ok(
            imageUploadService.modifyMedias(request.getFiles(), boardId, userId));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteImage(
        @PathVariable final Long boardId,
        @LoginUser final String userId
    ) {
        imageUploadService.deleteMedias(boardId, userId);
        return ResponseEntity.noContent().build();
    }
}

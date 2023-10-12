package com.ssafy.enjoytrip.core.board.controller;

import com.ssafy.enjoytrip.core.board.model.dto.response.BoardImageUrlResponse;
import com.ssafy.enjoytrip.core.board.service.BoardImageService;
import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/board/media")
@RequiredArgsConstructor
public class BoardImageController {

    private final BoardImageService imageService;

    @PostMapping("/{boardId}")
    public ResponseEntity<List<BoardImageUrlResponse>> uploadImage(
        @PathVariable final Long boardId,
        @RequestParam("files") final List<MultipartFile> files
    ) {
        return ResponseEntity.ok(imageService.uploadMedias(files, "board/" + boardId));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteImage(
        @PathVariable final Long boardId,
        @LoginUser final String userId
    ) {
        imageService.deleteMedias(boardId);
        return ResponseEntity.ok().build();
    }
}

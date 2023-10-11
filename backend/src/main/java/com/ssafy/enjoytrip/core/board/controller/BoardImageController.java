package com.ssafy.enjoytrip.core.board.controller;

import com.ssafy.enjoytrip.core.media.service.MediaService;
import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/image")
public class BoardImageController {

    private final MediaService mediaService;

    @PostMapping("/{boardId}")
    public ResponseEntity<Void> uploadImage(
        @PathVariable final Long boardId,
        @RequestParam("files") final List<MultipartFile> files
    ) {
        mediaService.insertMedias(boardId, files, "/board");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<Void> modifyImage(
        @PathVariable final Long boardId,
        @LoginUser final String userId,
        @RequestParam("files") final List<MultipartFile> files
    ) {
        
        return ResponseEntity.ok().build();
    }
}

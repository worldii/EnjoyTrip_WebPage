package com.ssafy.enjoytrip.core.hotplace.controller;

import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceArticleSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceVoteRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceArticleResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceResponse;
import com.ssafy.enjoytrip.core.hotplace.service.HotPlaceService;
import com.ssafy.enjoytrip.core.media.model.FileUrlResponse;
import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import com.ssafy.enjoytrip.global.error.PageInfoRequest;
import com.ssafy.enjoytrip.infra.S3Service;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/hotplace")
public class HotPlaceController {

    private final HotPlaceService hotPlaceService;
    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<String> insertHotPlace(
        @RequestBody final HotPlaceSaveRequest request
    ) {
        final String hotPlaceId = hotPlaceService.insertHotPlace(request);

        return ResponseEntity.created(URI.create("/hotplace" + hotPlaceId)).body(hotPlaceId);
    }

    @PostMapping("/{hotPlaceId}")
    public ResponseEntity<Long> intsertHotPlaceArticle(
        @PathVariable final String hotPlaceId,
        @RequestBody final HotPlaceArticleSaveRequest request,
        @LoginUser final String userId
    ) {
        final Long articleId = hotPlaceService.insertHotPlaceArticle(hotPlaceId, request, userId);

        return ResponseEntity.created(URI.create("/hotplace/" + hotPlaceId)).body(articleId);
    }

    @NoAuth
    @GetMapping
    public ResponseEntity<List<HotPlaceResponse>> getHotPlaceList(
        @ModelAttribute final PageInfoRequest pageInfoRequest,
        @RequestParam final String keyword
    ) {
        return ResponseEntity.ok(hotPlaceService.selectAllHotPlace(pageInfoRequest, keyword));
    }

    @NoAuth
    @GetMapping("/{hotPlaceId}")
    public ResponseEntity<HotPlaceResponse> getHotPlaceDetail(
        @PathVariable final String hotPlaceId
    ) {
        return ResponseEntity.ok(hotPlaceService.selectHotPlaceByHotPlaceId(hotPlaceId));
    }

    @NoAuth
    @GetMapping("/{hotPlaceId}/{articleId}")
    public ResponseEntity<HotPlaceArticleResponse> getHotPlaceArticleDetail(
        @PathVariable final String hotPlaceId,
        @PathVariable final Long articleId
    ) {
        return ResponseEntity.ok(
            hotPlaceService.selectHotPlaceArticleByArticleId(hotPlaceId, articleId));
    }


    @PutMapping("/{hotPlaceId}")
    public ResponseEntity<Void> voteHotPlace(
        @PathVariable final String hotPlaceId,
        @RequestBody final HotPlaceVoteRequest hotPlaceVoteRequest
    ) {
        hotPlaceService.updateVoteCount(hotPlaceId, hotPlaceVoteRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/article/{articleId}/flleUpload")
    public ResponseEntity<Boolean> uploadImagetoArticle(
        @PathVariable final int articleId,
        @ModelAttribute final List<MultipartFile> files
    ) {
        final List<String> strings = s3Service.uploadMedias(files, "hotplace/")
            .stream()
            .map(FileUrlResponse::getUrl)
            .collect(Collectors.toList());

        hotPlaceService.updateHotPlaceArticleImage(articleId, strings.get(0));
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{hotPlaceId}/tag")
    public ResponseEntity<Boolean> updateHotPlaceTag(@PathVariable String hotPlaceId,
        @RequestBody List<String> tagList) {
        hotPlaceService.updateHotPlaceTagList(hotPlaceId, tagList);
        return ResponseEntity.ok(true);
    }
}

package com.ssafy.enjoytrip.core.hotplace.controller;


import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceArticleSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSearchRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceVoteRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceArticleResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceDetailResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceResponse;
import com.ssafy.enjoytrip.core.hotplace.service.HotPlaceService;
import com.ssafy.enjoytrip.global.auth.model.dto.LoginUser;
import com.ssafy.enjoytrip.global.auth.model.dto.NoAuth;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotplace")
public class HotPlaceController {

    private final HotPlaceService hotPlaceService;

    @PostMapping
    public ResponseEntity<String> insertHotPlace(@RequestBody final HotPlaceSaveRequest request) {
        final String hotPlaceId = hotPlaceService.insertHotPlace(request);

        return ResponseEntity.created(URI.create("/hotplace" + hotPlaceId)).body(hotPlaceId);
    }

    @PostMapping("/{hotPlaceId}")
    public ResponseEntity<Long> insertHotPlaceArticle(
            @PathVariable final String hotPlaceId,
            @RequestBody final HotPlaceArticleSaveRequest request,
            @LoginUser final String userId) {
        final Long articleId = hotPlaceService.insertHotPlaceArticle(hotPlaceId, request, userId);

        return ResponseEntity.created(URI.create("/hotplace/" + hotPlaceId + articleId))
                .body(articleId);
    }

    @NoAuth
    @GetMapping
    public ResponseEntity<List<HotPlaceResponse>> getHotPlaceList(
            @ModelAttribute final HotPlaceSearchRequest searchRequest) {
        return ResponseEntity.ok(hotPlaceService.selectAllHotPlace(searchRequest));
    }

    @NoAuth
    @GetMapping("/{hotPlaceId}")
    public ResponseEntity<HotPlaceDetailResponse> getHotPlaceDetail(
            @PathVariable final String hotPlaceId) {
        return ResponseEntity.ok(hotPlaceService.selectAllByHotPlaceId(hotPlaceId));
    }

    @NoAuth
    @GetMapping("/{hotPlaceId}/article/{articleId}")
    public ResponseEntity<HotPlaceArticleResponse> getHotPlaceArticleDetail(
            @PathVariable final String hotPlaceId, @PathVariable final Long articleId) {
        return ResponseEntity.ok(
                hotPlaceService.selectHotPlaceArticleByArticleId(hotPlaceId, articleId));
    }

    @PutMapping("/{hotPlaceId}/vote")
    public ResponseEntity<Void> voteHotPlace(
            @PathVariable final String hotPlaceId,
            @RequestBody final HotPlaceVoteRequest hotPlaceVoteRequest) {
        hotPlaceService.updateVoteCount(hotPlaceId, hotPlaceVoteRequest);
        return ResponseEntity.ok().build();
    }
}

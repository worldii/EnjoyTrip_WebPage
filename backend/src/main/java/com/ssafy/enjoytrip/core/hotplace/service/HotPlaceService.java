package com.ssafy.enjoytrip.core.hotplace.service;


import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceArticleSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSearchRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceVoteRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceArticleResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceDetailResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceResponse;
import java.util.List;

public interface HotPlaceService {

    String insertHotPlace(final HotPlaceSaveRequest hotPlace);

    Long insertHotPlaceArticle(
            final String hotPlaceId,
            final HotPlaceArticleSaveRequest hotPlaceArticle,
            final String userId);

    List<HotPlaceResponse> selectAllHotPlace(final HotPlaceSearchRequest hotPlaceSearchRequest);

    HotPlaceDetailResponse selectAllByHotPlaceId(final String hotPlaceId);

    HotPlaceArticleResponse selectHotPlaceArticleByArticleId(
            final String hotPlaceId, final Long articleId);

    void updateVoteCount(final String hotPlaceId, final HotPlaceVoteRequest voteRequest);
}

package com.ssafy.enjoytrip.core.hotplace.service;

import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceArticleSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceSaveRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.request.HotPlaceVoteRequest;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceArticleResponse;
import com.ssafy.enjoytrip.core.hotplace.model.dto.response.HotPlaceResponse;
import com.ssafy.enjoytrip.global.error.PageInfoRequest;
import java.util.List;

public interface HotPlaceService {

    String insertHotPlace(final HotPlaceSaveRequest hotPlace);

    Long insertHotPlaceArticle(
        final String hotPlaceId,
        final HotPlaceArticleSaveRequest hotPlaceArticle,
        final String userId);

    HotPlaceResponse selectHotPlaceByHotPlaceId(final String hotPlaceId);

    HotPlaceArticleResponse selectHotPlaceArticleByArticleId(
        final String hotPlaceId,
        final Long articleId
    );

    List<HotPlaceResponse> selectAllHotPlace(
        final PageInfoRequest pageInfoRequest,
        final String keyword
    );

    void updateVoteCount(final String hotPlaceId, final HotPlaceVoteRequest voteRequest);


    int updateHotPlaceArticleImage(final int hotPlaceArticleId, final String imageUrl);

    int updateHotPlaceTag(String hotPlaceId, String tagId);

    void updateHotPlaceTagList(String hotPlaceId, List<String> tagIdList);

    int insertHotPlaceTag(String hotPlaceId, String tagName);
}

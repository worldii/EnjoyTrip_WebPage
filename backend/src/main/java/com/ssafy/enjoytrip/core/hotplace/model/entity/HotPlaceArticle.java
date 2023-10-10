package com.ssafy.enjoytrip.core.hotplace.model.entity;

import com.ssafy.enjoytrip.global.error.HotPlaceException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HotPlaceArticle {

    private Long hotPlaceArticleId;
    private String hotPlaceId;
    private String hotPlaceName;
    private String userId;
    private String content;
    private String createAt;
    private String imageUrl;

    @Builder
    public HotPlaceArticle(
        final Long hotPlaceArticleId, final String hotPlaceId, final String hotPlaceName,
        final String userId, final String content, final String createAt, final String imageUrl
    ) {
        validateHotPlaceId(hotPlaceId);
        validateUserId(userId);
        validateContent(content);
        validateImageUrl(imageUrl);
        this.hotPlaceArticleId = hotPlaceArticleId;
        this.hotPlaceId = hotPlaceId;
        this.hotPlaceName = hotPlaceName;
        this.userId = userId;
        this.content = content;
        this.createAt = createAt;
        this.imageUrl = imageUrl;
    }

    private void validateImageUrl(final String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new HotPlaceException("이미지 URL이 없습니다.");
        }

    }

    private void validateContent(final String content) {
        if (content == null || content.isEmpty()) {
            throw new HotPlaceException("내용이 없습니다.");
        }
    }

    private void validateUserId(final String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new HotPlaceException("유저 아이디가 없습니다.");
        }
    }

    private void validateHotPlaceId(final String hotPlaceId) {
        if (hotPlaceId == null || hotPlaceId.isEmpty()) {
            throw new HotPlaceException("핫플레이스 아이디가 없습니다.");
        }
    }
}

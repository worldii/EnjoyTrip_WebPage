package com.ssafy.enjoytrip.core.plan.model.entity;


import com.ssafy.enjoytrip.global.error.PlanException;
import java.sql.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlanBoard {

    private Long planBoardId;
    private String userId;
    private String title;
    private Date startDate;
    private Date endDate;

    @Builder
    public PlanBoard(
        final Long planBoardId,
        final String userId,
        final String title,
        final Date startDate,
        final Date endDate
    ) {
        validateUserId(userId);
        validateTitle(title);
        validateDate(startDate, endDate);

        this.planBoardId = planBoardId;
        this.userId = userId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateUserId(String userId) {
        if (userId == null) {
            throw new PlanException("userId는 null일 수 없습니다.");
        }
    }

    private void validateTitle(String title) {
        if (title == null) {
            throw new PlanException("title은 null일 수 없습니다.");
        }
    }

    private void validateDate(Date startDate, Date endDate) {
        validateStartDate(startDate);
        validateEndDate(endDate);
        if (startDate.after(endDate)) {
            throw new PlanException("startDate는 endDate보다 빠를 수 없습니다.");
        }
    }

    private void validateEndDate(Date endDate) {
        if (endDate == null) {
            throw new PlanException("endDate는 null일 수 없습니다.");
        }
    }

    private void validateStartDate(Date startDate) {
        if (startDate == null) {
            throw new PlanException("startDate는 null일 수 없습니다.");
        }
    }
}

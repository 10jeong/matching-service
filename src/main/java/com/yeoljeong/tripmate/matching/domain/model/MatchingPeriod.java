package com.yeoljeong.tripmate.matching.domain.model;

import static com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode.RECRUITMENT_DEADLINE_AFTER_ACTIVITY_DATE;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchingPeriod {

	@Column(nullable = false)
	private LocalDateTime scheduledAt;

	@Column(nullable = false)
	private LocalDateTime recruitDeadline;

	public static MatchingPeriod of(LocalDateTime scheduledAt, LocalDateTime recruitDeadline) {
		if (recruitDeadline.isAfter(scheduledAt)) {
			throw new BusinessException(RECRUITMENT_DEADLINE_AFTER_ACTIVITY_DATE);
		}
		return new MatchingPeriod(scheduledAt, recruitDeadline);
	}

	boolean isExpired() {
		return LocalDateTime.now().isAfter(recruitDeadline);
	}
}

package com.yeoljeong.tripmate.matching.domain.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingPeriod {

	@Column(nullable = false)
	private LocalDateTime scheduledAt;

	@Column(nullable = false)
	private LocalDateTime recruitDeadline;

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(recruitDeadline);
	}
}

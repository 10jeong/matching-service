package com.yeoljeong.tripmate.matching.application.dto.result;

import com.yeoljeong.tripmate.matching.domain.constants.MatchingStatus;
import com.yeoljeong.tripmate.matching.domain.model.Location;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import com.yeoljeong.tripmate.matching.domain.model.MatchingPeriod;
import com.yeoljeong.tripmate.matching.domain.model.MatchingSetting;
import java.util.UUID;

public record MatchingDetailResult(
	UUID id,
	UUID hostUserId,
	String title,
	String description,
	Location location,
	MatchingPeriod period,
	String chatUrl,
	MatchingStatus status,
	MatchingSetting setting
) {

	public static MatchingDetailResult from(Matching matching) {
		return new MatchingDetailResult(
			matching.getId(),
			matching.getHostUserId(),
			matching.getTitle(),
			matching.getDescription(),
			matching.getLocation(),
			matching.getMatchingPeriod(),
			matching.getChatUrl(),
			matching.getStatus(),
			matching.getMatchingSetting()
		);
	}
}

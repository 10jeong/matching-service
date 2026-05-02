package com.yeoljeong.tripmate.matching.application.external;

import com.yeoljeong.tripmate.matching.domain.model.Matching;

public interface MatchingEventPublisher {
	void publishMatchingCreated(Matching matching);
}

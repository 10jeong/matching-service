package com.yeoljeong.tripmate.matching.application.external;

import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.util.UUID;

public interface MatchingEventPort {
	void appendMatchingCreated(Matching matching);
	void appendMatchingAccomplished(Matching matching);
	void appendMateSubscribed(UUID userId);
	void appendMateUnsubscribed(UUID userId);
}

package com.yeoljeong.tripmate.matching.application.external;

import com.yeoljeong.tripmate.matching.domain.model.Matching;

public interface MatchingEventPort {
	void appendMatchingCreated(Matching matching);
	void appendMatchingAccomplished(Matching matching);
}

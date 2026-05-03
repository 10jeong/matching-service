package com.yeoljeong.tripmate.matching.application.external;

import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.security.NoSuchAlgorithmException;

public interface MatchingEventPublisher {
	void publishMatchingCreated(Matching matching) throws NoSuchAlgorithmException;
}

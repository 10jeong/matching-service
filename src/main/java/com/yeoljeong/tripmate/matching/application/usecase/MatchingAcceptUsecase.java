package com.yeoljeong.tripmate.matching.application.usecase;

import java.util.UUID;

public interface MatchingAcceptUsecase {
	void accept(UUID userId, UUID matchingId);
}

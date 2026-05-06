package com.yeoljeong.tripmate.matching.application.external;

import java.util.List;
import java.util.UUID;

public interface MatchingCandidateStore {
	void save(UUID userId, List<UUID> candidateIds);
	List<UUID> get(UUID hostUserId);
	void delete(UUID hostUserId);
}

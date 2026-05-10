package com.yeoljeong.tripmate.matching.application.external;

import java.util.List;
import java.util.UUID;

public interface MatchingCandidateStore {
	void save(UUID matchingId, List<UUID> candidateIds);
	List<UUID> get(UUID matchingId);
	void delete(UUID matchingId);
}

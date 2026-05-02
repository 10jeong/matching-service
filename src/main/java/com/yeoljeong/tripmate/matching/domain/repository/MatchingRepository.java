package com.yeoljeong.tripmate.matching.domain.repository;

import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.util.Optional;
import java.util.UUID;

public interface MatchingRepository {
	boolean existsByHostUserIdAndMatchingStatusOpen(UUID hostId);
	Optional<Matching> findByHostUserIdAndMatchingStatusOpen(UUID hostId);
	Matching save(Matching matching);
}

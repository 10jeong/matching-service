package com.yeoljeong.tripmate.matching.domain.repository;

import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.util.UUID;

public interface MatchingRepository {
	boolean existsByHostUserIdAndMatchingStatusOpen(UUID hostId);
	Matching save(Matching matching);
}

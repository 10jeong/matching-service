package com.yeoljeong.tripmate.matching.domain.repository;

import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatchingRepository {
	boolean existsByHostUserIdAndMatchingStatusOpen(UUID hostId);
	Optional<Matching> findByHostUserIdAndMatchingStatusOpen(UUID hostId);
	Matching save(Matching matching);
	List<Matching> findAllByCriteria(UUID userId, String gender, boolean isSmoking,
		String mbtiIE, String mbtiSN, String mbtiTF, String mbtiPJ);
	Optional<Matching> findByIdAndIsDeletedFalse(UUID matchingId);
}

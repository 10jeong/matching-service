package com.yeoljeong.tripmate.matching.infrastructure.persistence.jpa;

import com.yeoljeong.tripmate.matching.domain.constants.MatchingStatus;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingJpaRepository extends JpaRepository<Matching, UUID> {
	boolean existsByHostUserIdAndStatus(UUID hostId, MatchingStatus status);
	Optional<Matching> findByHostUserIdAndStatus(UUID hostId, MatchingStatus status);
	Optional<Matching> findByIdAndIsDeletedFalse(UUID matchingId);
}

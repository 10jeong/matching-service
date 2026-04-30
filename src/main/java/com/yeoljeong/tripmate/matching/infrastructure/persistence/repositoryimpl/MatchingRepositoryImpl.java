package com.yeoljeong.tripmate.matching.infrastructure.persistence.repositoryimpl;

import com.yeoljeong.tripmate.matching.domain.constants.MatchingStatus;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import com.yeoljeong.tripmate.matching.infrastructure.persistence.jpa.MatchingJpaRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchingRepositoryImpl implements MatchingRepository {

	private final MatchingJpaRepository repository;

	@Override
	public boolean existsByHostUserIdAndMatchingStatusOpen(UUID hostId) {
		return repository.existsByHostUserIdAndStatus(hostId, MatchingStatus.OPEN);
	}

	@Override
	public Matching save(Matching matching) {
		return repository.save(matching);
	}
}

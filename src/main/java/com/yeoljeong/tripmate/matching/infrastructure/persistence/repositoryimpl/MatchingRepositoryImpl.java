package com.yeoljeong.tripmate.matching.infrastructure.persistence.repositoryimpl;

import com.yeoljeong.tripmate.matching.domain.constants.MatchingStatus;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import com.yeoljeong.tripmate.matching.infrastructure.persistence.jpa.MatchingJpaRepository;
import com.yeoljeong.tripmate.matching.infrastructure.persistence.querydsl.MatchingQueryDslRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchingRepositoryImpl implements MatchingRepository {

	private final MatchingJpaRepository repository;
	private final MatchingQueryDslRepository queryDslRepository;

	@Override
	public boolean existsByHostUserIdAndMatchingStatusOpen(UUID hostId) {
		return repository.existsByHostUserIdAndStatus(hostId, MatchingStatus.OPEN);
	}

	@Override
	public Optional<Matching> findByHostUserIdAndMatchingStatusOpen(UUID hostId) {
		return repository.findByHostUserIdAndStatus(hostId, MatchingStatus.OPEN);
	}

	@Override
	public Matching save(Matching matching) {
		return repository.save(matching);
	}

	@Override
	public List<Matching> findAllByCriteria(UUID userId, String gender, boolean isSmoking,
		String mbtiIE, String mbtiSN, String mbtiTF, String mbtiPJ) {
		return queryDslRepository.findAllByCriteria(
			userId, gender, isSmoking,
			mbtiIE, mbtiSN, mbtiTF, mbtiPJ
		);
	}
}

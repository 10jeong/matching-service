package com.yeoljeong.tripmate.matching.infrastructure.persistence.repositoryimpl;

import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchingRepositoryImpl implements MatchingRepository {

	private final com.yeoljeong.tripmate.matching.infrastructure.persistence.jpa.MatchingJpaRepository matchingRepository;

}

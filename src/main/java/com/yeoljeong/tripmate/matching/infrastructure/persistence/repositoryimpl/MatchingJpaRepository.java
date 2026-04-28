package com.yeoljeong.tripmate.matching.infrastructure.persistence.repositoryimpl;

import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import com.yeoljeong.tripmate.matching.infrastructure.persistence.jpa.SpringDataMatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchingJpaRepository implements MatchingRepository {

	private final SpringDataMatchingRepository matchingRepository;

}

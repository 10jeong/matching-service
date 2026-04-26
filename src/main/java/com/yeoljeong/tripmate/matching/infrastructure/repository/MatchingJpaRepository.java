package com.yeoljeong.tripmate.matching.infrastructure.repository;

import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import com.yeoljeong.tripmate.matching.infrastructure.repository.jpa.SpringDataMatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchingJpaRepository implements MatchingRepository {

	private final SpringDataMatchingRepository matchingRepository;

}

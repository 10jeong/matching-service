package com.yeoljeong.tripmate.matching.infrastructure.repository;

import com.yeoljeong.tripmate.matching.infrastructure.repository.jpa.SpringDataMatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchingJpaRepository {

	private final SpringDataMatchingRepository matchingRepository;

}

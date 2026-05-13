package com.yeoljeong.tripmate.matching.application.usecase;

import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingQueryService {

	private final MatchingRepository repository;

	public boolean withdrawCheck(UUID userId) {
		return repository.existsByHostUserIdAndMatchingStatusOpen(userId) &&
			repository.existsByHostUserIdAndMatchingStatusMatched(userId) &&
			repository.existsByMateUserIdAndMatchingStatusMatched(userId);
	}
}

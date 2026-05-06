package com.yeoljeong.tripmate.matching.infrastructure.redis;

import com.yeoljeong.tripmate.matching.application.external.MatchingCandidateStore;
import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MatchingCandidateRedisStore implements MatchingCandidateStore {

	private final static String MATCHING_CANDIDATE_KEY_PREFIX = "matching:candidate:";

	private final RedisTemplate<String, Object> redisTemplate;
	private final MatchingRepository repository;

	@Override
	public void save(UUID matchingId, List<UUID> candidateIds) {
		String key = MATCHING_CANDIDATE_KEY_PREFIX + matchingId;
		repository.findByHostUserIdAndMatchingStatusOpen(matchingId)
			.ifPresent(matching -> {
				Duration ttl = Duration.between(
					LocalDateTime.now(),
					matching.getMatchingPeriod().getRecruitDeadline()
				);
				if (ttl.isNegative() || ttl.isZero()) {
					log.warn("[Redis] recruitDeadline이 이미 지남 - matchingId: {}", matching.getId());
					return;
				}
				String[] candidateIdsStr = candidateIds.stream()
					.map(UUID::toString)
					.toArray(String[]::new);
				redisTemplate.opsForSet().add(key, candidateIdsStr);
				redisTemplate.expire(key, ttl);
				log.debug("[Redis] candidates 저장 - matchingId: {}, 후보수: {}",
					matching.getId(), candidateIds.size());
			});
	}

	@Override
	public List<UUID> get(UUID matchingId) {
		String key = MATCHING_CANDIDATE_KEY_PREFIX + matchingId;
		Set<Object> members = redisTemplate.opsForSet().members(key);
		if (members == null) return List.of();
		return members.stream()
			.map(id -> UUID.fromString(id.toString()))
			.toList();
	}

	@Override
	public void delete(UUID matchingId) {
		redisTemplate.delete(MATCHING_CANDIDATE_KEY_PREFIX + matchingId);
	}
}

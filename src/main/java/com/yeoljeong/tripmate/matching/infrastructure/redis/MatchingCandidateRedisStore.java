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
	public void save(UUID hostUserId, List<UUID> candidateIds) {
		String key = MATCHING_CANDIDATE_KEY_PREFIX + hostUserId;
		repository.findByHostUserIdAndMatchingStatusOpen(hostUserId)
			.ifPresent(matching -> {
				Duration ttl = Duration.between(
					LocalDateTime.now(),
					matching.getMatchingPeriod().getRecruitDeadline()
				);
				candidateIds.forEach(userId ->
					redisTemplate.opsForSet().add(key, userId.toString())
				);
				redisTemplate.expire(key, ttl);
				log.debug("[Redis] candidats 저장 - matchingId: {}, 후보수: {}",
					matching.getId(), candidateIds.size());
			});
	}

	@Override
	public List<UUID> getAndDelete(UUID hostUserId) {
		String key = MATCHING_CANDIDATE_KEY_PREFIX + hostUserId;
		Set<Object> members = redisTemplate.opsForSet().members(key);
		redisTemplate.delete(key);

		if (members == null) return List.of();

		return members.stream()
			.map(id -> UUID.fromString(id.toString()))
			.toList();
	}
}

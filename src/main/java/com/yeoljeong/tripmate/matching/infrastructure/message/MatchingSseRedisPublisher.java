package com.yeoljeong.tripmate.matching.infrastructure.message;

import com.yeoljeong.tripmate.common.message.MatchingSsePayload;
import com.yeoljeong.tripmate.matching.application.external.MatchingCandidateNotifier;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MatchingSseRedisPublisher implements MatchingCandidateNotifier {

	private final static String CHANNEL_PREFIX = "matching:sse:";

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void publishToUsers(List<UUID> userIds, Matching matching) {
		userIds.forEach(userId -> publish(userId, matching));
	}

	private void publish(UUID userId, Matching matching) {
		String channel = CHANNEL_PREFIX + userId;
		log.info("[Redis] SSE 발행 - channel: {}", channel); // 추가
		try {
			redisTemplate.convertAndSend(channel, toMatchingSsePayload(matching));
		} catch (Exception e) {
			log.error("[MatchingSSE] Redis publish failed - channel: {}, error : {}",
				channel, e.getMessage());
			throw e;
		}
	}

	private MatchingSsePayload toMatchingSsePayload(Matching matching) {
		return new MatchingSsePayload(
			matching.getId(),
			matching.getHostUserId(),
			matching.getTitle(),
			matching.getDescription()
		);
	}

}

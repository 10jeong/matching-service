package com.yeoljeong.tripmate.matching.infrastructure.message;

import com.yeoljeong.tripmate.common.message.MatchingSsePayload;
import com.yeoljeong.tripmate.matching.application.external.MatchingNotifier;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MatchingSseRedisPublisher implements MatchingNotifier {

	private final static String CHANNEL_PREFIX = "matching:sse:";
	private static final String GUARD_KEY_PREFIX = "sse:sent:";

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void publishToUsers(List<UUID> userIds, Matching matching) {
		userIds.forEach(userId -> publishWithGuard(userId, matching));
	}

	@Override
	public void publishToUserDirect(UUID userId, Matching matching) {
		publish(userId, matching);
	}

	private void publishWithGuard(UUID userId, Matching matching) {
		String guardKey = GUARD_KEY_PREFIX + matching.getId() + ":" + userId;
		Boolean isNew = redisTemplate.opsForValue()
			.setIfAbsent(guardKey, "1", Duration.ofMinutes(10));

		if (Boolean.FALSE.equals(isNew)) {
			log.debug("[MatchingSSE] 중복 발송 방지 - userId: {}, matchingId: {}", userId, matching.getId());
			return;
		}
		publish(userId, matching);
	}

	private void publish(UUID userId, Matching matching) {

		String channel = CHANNEL_PREFIX + userId;
		try {
			redisTemplate.convertAndSend(channel, toMatchingSsePayload(matching));
		} catch (Exception e) {
			log.error("[MatchingSSE] Redis publish failed - channel: {}, error : {}", channel, e.getMessage());
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

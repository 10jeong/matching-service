package com.yeoljeong.tripmate.usersetting.infrastructure.message;

import com.yeoljeong.tripmate.event.EventUtils;
import com.yeoljeong.tripmate.event.MatchingCandidatesFoundEvent;
import com.yeoljeong.tripmate.usersetting.application.external.UserSettingEventPublisher;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSettingEventKafkaPublisher implements UserSettingEventPublisher {

	private final static String MATCHING_CANDIDATES_FOUND_TOPIC = "matching.candidates.found";

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void publishMatchingCandidates(UUID hostUserId, List<UUID> candidates)
		throws NoSuchAlgorithmException {
		log.info("[Kafka] matching.candidates.found 발행 - hostUserId: {}, 후보수: {}",
			hostUserId, candidates.size()); // 추가
		MatchingCandidatesFoundEvent event = toMatchingCandidatesFoundEvent(hostUserId, candidates);
		kafkaTemplate.send(MATCHING_CANDIDATES_FOUND_TOPIC, event.hostUserId().toString(), event)
			.whenComplete((result, ex) -> {
				if (ex != null) {
					log.error("[Kafka] matching.candidates.found 발행 실패 - hostUserId: {}", event.hostUserId());
				} else {
					log.info("[Kafka] matching.candidates.found 발행 성공 - hostUserId: {}",
						event.hostUserId());
				}
			});
	}

	private MatchingCandidatesFoundEvent toMatchingCandidatesFoundEvent(UUID hostUserId, List<UUID> candidates)
		throws NoSuchAlgorithmException {
		return new MatchingCandidatesFoundEvent(
			EventUtils.getEventHash("matching", String.valueOf(hostUserId), LocalDateTime.now()),
			hostUserId, candidates
		);
	}
}

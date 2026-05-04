package com.yeoljeong.tripmate.matching.infrastructure.message;

import com.yeoljeong.tripmate.event.EventUtils;
import com.yeoljeong.tripmate.event.MatchingCreateEvent;
import com.yeoljeong.tripmate.event.MatchingMatchedEvent;
import com.yeoljeong.tripmate.event.enums.MatchingTopic;
import com.yeoljeong.tripmate.matching.application.external.MatchingEventPublisher;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatchingEventKafkaPublisher implements MatchingEventPublisher {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void publishMatchingCreated(Matching matching) throws NoSuchAlgorithmException {
		MatchingCreateEvent event = toMatchingCreatedEvent(matching);
		kafkaTemplate.send(MatchingTopic.MATCHING_CREATED_TOPIC, event.matchingId().toString(), event)
			.whenComplete((result, ex) -> {
				if (ex != null) {
					log.error("[Kafka] matching.created 발행 실패 - matchingId: {}", event.matchingId(), ex);
				} else {
					log.info("[Kafka] matching.created 발행 성공 - matchingId: {}", event.matchingId());
				}
			});
	}

	@Override
	public void publishMatchingAccomplished(Matching matching) {
		MatchingMatchedEvent event = toMatchingMatchedEvent(matching);
		kafkaTemplate.send(MatchingTopic.MATCHING_MATCHED_TOPIC, event.matchingId().toString(), event)
			.whenComplete((result, ex) -> {
				if (ex != null) {
					log.error("[MATCHING_KAFKA_PUBLISHER] matching.matched 발행 실패 - matchingId: {}", event.matchingId());
				}
			});
	}

	private MatchingMatchedEvent toMatchingMatchedEvent(Matching matching) {
		try {
			return new MatchingMatchedEvent(
				EventUtils.getEventHash("matching", matching.getId().toString(), matching.getUpdatedAt()),
				matching.getId(), matching.getHostUserId(), matching.getMateUserId(), matching.getTitle()
			);
		} catch (NoSuchAlgorithmException e) {
			log.error("[MATCHING_KAFKA_PUBLISHER] matching.matched 이벤트 해시 생성 실패 - matching id: {}", matching.getId());
			throw new RuntimeException(e);
		}
	}

	private MatchingCreateEvent toMatchingCreatedEvent(Matching matching)
		throws NoSuchAlgorithmException {
		return new MatchingCreateEvent(
			EventUtils.getEventHash("matching", matching.getId().toString(), matching.getUpdatedAt()),
			matching.getId(), matching.getHostUserId(), matching.getTitle(),
			matching.getMatchingSetting().isAllowSmoking(),
			matching.getMatchingSetting().getPreferenceMbtiIE().name(),
			matching.getMatchingSetting().getPreferenceMbtiSN().name(),
			matching.getMatchingSetting().getPreferenceMbtiTF().name(),
			matching.getMatchingSetting().getPreferenceMbtiPJ().name(),
			matching.getMatchingSetting().getPreferenceGender().name()
		);
	}
}

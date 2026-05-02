package com.yeoljeong.tripmate.matching.infrastructure.message;

import com.yeoljeong.tripmate.event.MatchingCreateEvent;
import com.yeoljeong.tripmate.event.enums.MatchingTopic;
import com.yeoljeong.tripmate.matching.application.external.MatchingEventPublisher;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
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
	public void publishMatchingCreated(Matching matching) {
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

	private MatchingCreateEvent toMatchingCreatedEvent(Matching matching) {
		return new MatchingCreateEvent(
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

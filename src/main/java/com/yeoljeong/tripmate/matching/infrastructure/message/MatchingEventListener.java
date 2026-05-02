package com.yeoljeong.tripmate.matching.infrastructure.message;

import com.yeoljeong.tripmate.common.event.MatchingCandidatesFoundEvent;
import com.yeoljeong.tripmate.event.enums.MatchingTopic;
import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.matching.application.dto.command.NotifyMatchingCommand;
import com.yeoljeong.tripmate.matching.application.usecase.NotifyMatchingCandidatesUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MatchingEventListener {

	private final NotifyMatchingCandidatesUsecase notifyMatchingCandidatesUsecase;

	@KafkaListener(
		topics = MatchingTopic.MATCHING_CANDIDATES_FOUND_TOPIC,
		groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "kafkaListenerContainerFactory"
	)
	public void handleMatchingCandidatesFound(@Payload MatchingCandidatesFoundEvent event, Acknowledgment acknowledgment) {
		try {
			notifyMatchingCandidatesUsecase.sendMatchingInfo(
				new NotifyMatchingCommand(event.hostUserId(), event.userIds())
			);
			acknowledgment.acknowledge();
		}catch (BusinessException e){
			log.warn("[Matching] business failure consumed - hostUserId: {}, error: {}",
				event.hostUserId(), e.getMessage());
			acknowledgment.acknowledge();
		}catch (Exception e) {
			log.error("[Matching] SSE notify failed - hostUserId: {}, error: {}",
				event.hostUserId(), e.getMessage());
			throw e;
		}
	}

}

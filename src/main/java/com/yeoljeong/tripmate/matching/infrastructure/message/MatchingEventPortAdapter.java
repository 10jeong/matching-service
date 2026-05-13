package com.yeoljeong.tripmate.matching.infrastructure.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeoljeong.tripmate.common.message.MateConnectEvent;
import com.yeoljeong.tripmate.common.message.MateConnectEvent.Subscribe;
import com.yeoljeong.tripmate.common.message.MateConnectEvent.Unsubscribe;
import com.yeoljeong.tripmate.event.EventUtils;
import com.yeoljeong.tripmate.event.MatchingCreateEvent;
import com.yeoljeong.tripmate.event.MatchingMatchedEvent;
import com.yeoljeong.tripmate.event.enums.MatchingTopic;
import com.yeoljeong.tripmate.matching.application.external.MatchingEventPort;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import com.yeoljeong.tripmate.matching.infrastructure.persistence.MatchingOutbox;
import com.yeoljeong.tripmate.matching.infrastructure.persistence.jpa.MatchingOutboxRepository;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MatchingEventPortAdapter implements MatchingEventPort {

	private final ObjectMapper objectMapper;
	private final MatchingOutboxRepository matchingOutboxRepository;

	@Override
	public void appendMatchingCreated(Matching matching) {
		try {
			MatchingCreateEvent event = toMatchingCreatedEvent(matching);
			String payload = objectMapper.writeValueAsString(event);
			matchingOutboxRepository.save(MatchingOutbox.create(MatchingTopic.MATCHING_CREATED_TOPIC, payload));
		} catch (JsonProcessingException e) {
			log.error("[OUTBOX] matching.created 저장 실패 : matchingId = {}", matching.getId(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void appendMatchingAccomplished(Matching matching) {
		try {
			MatchingMatchedEvent event = toMatchingMatchedEvent(matching);
			String payload = objectMapper.writeValueAsString(event);
			matchingOutboxRepository.save(MatchingOutbox.create(MatchingTopic.MATCHING_MATCHED_TOPIC, payload));
		} catch (JsonProcessingException e) {
			log.error("[OUTBOX] matching.matched 저장 실패 : matchingId = {}", matching.getId(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void appendMateSubscribed(UUID userId) {
		try {
			MateConnectEvent.Subscribe event = new Subscribe(userId);
			String payload = objectMapper.writeValueAsString(event);
			matchingOutboxRepository.save(MatchingOutbox.create(
				MatchingTopic.MATCHING_MATE_SUBSCRIBED_TOPIC, payload));
		} catch (JsonProcessingException e) {
			log.error("[OUTBOX] mate.subscribe 저장 실패 : userId = {}", userId, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void appendMateUnsubscribed(UUID userId) {
		try {
			MateConnectEvent.Unsubscribe event = new Unsubscribe(userId);
			String payload = objectMapper.writeValueAsString(event);
			matchingOutboxRepository.save(MatchingOutbox.create(
				MatchingTopic.MATCHING_MATE_UNSUBSCRIBED_TOPIC, payload));
		} catch (JsonProcessingException e) {
			log.error("[OUTBOX] mate.unsubscribe 저장 실패 : userId = {}", userId, e);
			throw new RuntimeException(e);
		}
	}

	private MatchingMatchedEvent toMatchingMatchedEvent(Matching matching) {
		try {
			return new MatchingMatchedEvent(
				EventUtils.getEventHash("matching", matching.getId().toString(), matching.getUpdatedAt()),
				matching.getId(), matching.getHostUserId(), matching.getMateUserId(), matching.getTitle()
			);
		} catch (NoSuchAlgorithmException e) {
			log.error("[OUTBOX] matching.matched 이벤트 해시 생성 실패 - matching id: {}", matching.getId(), e);
			throw new RuntimeException(e);
		}
	}

	private MatchingCreateEvent toMatchingCreatedEvent(Matching matching) {
		try {
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
		} catch (NoSuchAlgorithmException e) {
			log.error("[OUTBOX] matching.created 이벤트 해시 생성 실패 - matching id: {}", matching.getId(), e);
			throw new RuntimeException(e);
		}
	}
}

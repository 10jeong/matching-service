package com.yeoljeong.tripmate.usersetting.infrastructure.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeoljeong.tripmate.event.EventUtils;
import com.yeoljeong.tripmate.event.MatchingCandidatesFoundEvent;
import com.yeoljeong.tripmate.event.enums.MatchingTopic;
import com.yeoljeong.tripmate.usersetting.application.external.UserSettingEventPort;
import com.yeoljeong.tripmate.usersetting.infrastructure.persistence.UserSettingOutbox;
import com.yeoljeong.tripmate.usersetting.infrastructure.persistence.jpa.UserSettingOutboxRepository;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSettingEventPortAdapter implements UserSettingEventPort {

	private final ObjectMapper objectMapper;
	private final UserSettingOutboxRepository userSettingOutboxRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void appendCandidateFound(UUID matchingId, UUID hostUserId, List<UUID> candidates) {
		try {
			MatchingCandidatesFoundEvent event = toMatchingCandidatesFoundEvent(matchingId, hostUserId, candidates);
			String payload = objectMapper.writeValueAsString(event);
			userSettingOutboxRepository.save(
				UserSettingOutbox.create(MatchingTopic.MATCHING_CANDIDATES_FOUND_TOPIC, payload));
		} catch (JsonProcessingException e) {
			log.error("[Outbox] matching.candidates.found 저장 실패 - matchingId: {}", matchingId, e);
			throw new RuntimeException();
		}
	}

	private MatchingCandidatesFoundEvent toMatchingCandidatesFoundEvent(UUID matchingId, UUID hostUserId, List<UUID> candidates) {
		try {
			return new MatchingCandidatesFoundEvent(
				EventUtils.getEventHash("matching", String.valueOf(hostUserId), LocalDateTime.now()),
				matchingId, hostUserId, candidates
			);
		} catch (NoSuchAlgorithmException e) {
			log.error("[USER_SETTING_PORT_ADAPTER] matching.candidates.found 이벤트 해시 생성 실패 - matchingId: {}", matchingId, e);
			throw new RuntimeException(e);
		}
	}
}

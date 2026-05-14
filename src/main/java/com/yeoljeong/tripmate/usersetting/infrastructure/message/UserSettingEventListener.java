package com.yeoljeong.tripmate.usersetting.infrastructure.message;

import com.yeoljeong.tripmate.common.infrastructure.KafkaPayloadDeserializer;
import com.yeoljeong.tripmate.common.message.MateConnectEvent;
import com.yeoljeong.tripmate.event.MatchingCreateEvent;
import com.yeoljeong.tripmate.event.MatchingMatchedEvent;
import com.yeoljeong.tripmate.event.UserCreatedEvent;
import com.yeoljeong.tripmate.event.enums.MatchingTopic;
import com.yeoljeong.tripmate.event.enums.UserTopic;
import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.usersetting.application.dto.command.CreateUserSettingCommand;
import com.yeoljeong.tripmate.usersetting.application.dto.command.MatchingCandidateCriteria;
import com.yeoljeong.tripmate.usersetting.application.usecase.CreateUserSettingUsecase;
import com.yeoljeong.tripmate.usersetting.application.usecase.EnabledMatchingSettingUsecase;
import com.yeoljeong.tripmate.usersetting.application.usecase.FindEnableMatchingUserUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserSettingEventListener {

	private final CreateUserSettingUsecase createUserSettingUsecase;
	private final EnabledMatchingSettingUsecase enabledMatchingSettingUsecase;
	private final FindEnableMatchingUserUsecase	findEnableMatchingUserUsecase;
	private final KafkaPayloadDeserializer deserializer;

	@KafkaListener(
		topics = UserTopic.USER_CREATED_TOPIC,
		groupId = "matching-usersetting-group",
		containerFactory = "kafkaListenerContainerFactory"
	)
	public void create(@Payload String payload, Acknowledgment acknowledgment) {
		UserCreatedEvent event = deserializer.deserialize(payload, UserCreatedEvent.class);
		try {
			createUserSettingUsecase.create(CreateUserSettingCommand.of(event.userId(), event.gender()));
			acknowledgment.acknowledge();
		} catch (BusinessException e) {
			log.warn("[UserSetting] already exists: userId: {}", event.userId());
			acknowledgment.acknowledge();
		} catch (Exception e) {
			log.warn("[UserSetting] Retry scheduled for userSetting creation (unknown error): userId: {}, error: {}",
				event.userId(), e.getMessage());
			throw e;
		}
	}

	@KafkaListener(
		topics = MatchingTopic.MATCHING_CREATED_TOPIC,
		groupId = "matching-usersetting-group",
		containerFactory = "kafkaListenerContainerFactory"
	)
	public void findEnableMatchingUser(@Payload String payload, Acknowledgment acknowledgment) {
		MatchingCreateEvent event = deserializer.deserialize(payload, MatchingCreateEvent.class);
		log.info("[UserSetting] matching.created 수신 - matchingId: {}", event.matchingId());
		try {
			findEnableMatchingUserUsecase.findAllEnableMatchingUser(
				new MatchingCandidateCriteria(
					event.matchingId(),
					event.hostUserId(),
					event.latitude(),
					event.longitude(),
					event.allowSmoking(),
					event.preferenceMbtiIE(),
					event.preferenceMbtiSN(),
					event.preferenceMbtiTF(),
					event.preferenceMbtiPJ(),
					event.preferenceGender()
				)
			);
			acknowledgment.acknowledge();
		} catch (BusinessException e) {
			log.warn("[USER_SETTING_LISTENER] 매칭 후보 찾기 스킵(비즈니스 에러): matchingId: {}, error: {}",
				event.matchingId(), e.getMessage());
			acknowledgment.acknowledge();
		} catch (Exception e) {
			log.warn("[USER_SETTING_LISTENER] 매칭 후보 찾기 재시도(unknown 에러) : userId: {}, error: {}",
				event.hostUserId(), e.getMessage());
			throw e;
		}
	}

	@KafkaListener(
		topics = MatchingTopic.MATCHING_MATE_SUBSCRIBED_TOPIC,
		groupId = "matching-usersetting-group",
		containerFactory = "kafkaListenerContainerFactory"
	)
	public void mateSubscribed(String payload, Acknowledgment acknowledgment) {
		MateConnectEvent.Subscribe event = deserializer.deserialize(payload, MateConnectEvent.Subscribe.class);
		try {
			enabledMatchingSettingUsecase.activateMatching(event.userId());
			acknowledgment.acknowledge();
		} catch (BusinessException e) {
			log.warn("[USER_SETTING_LISTENER] 메이트 매칭 가능 상태 true 변경 실패(비즈니스 에러): userId = {}, error = {}",
				event.userId(), e.getMessage(), e);
			acknowledgment.acknowledge();
		} catch (Exception e) {
			log.warn("[USER_SETTING_LISTENER] 메이트 매칭 가능 상태 true 재시도(unknown 에러): userId = {}, error = {}",
				event.userId(), e.getMessage(), e);
			throw e;
		}
	}

	@KafkaListener(
		topics = MatchingTopic.MATCHING_MATE_UNSUBSCRIBED_TOPIC,
		groupId = "matching-usersetting-group",
		containerFactory = "kafkaListenerContainerFactory"
	)
	public void mateUnsubscribed(String payload, Acknowledgment acknowledgment) {
		MateConnectEvent.Unsubscribe event = deserializer.deserialize(payload, MateConnectEvent.Unsubscribe.class);
		try {
			enabledMatchingSettingUsecase.deactivateMatching(event.userId());
			acknowledgment.acknowledge();
		} catch (BusinessException e) {
			log.warn("[USER_SETTING_LISTENER] 메이트 매칭 가능 상태 false 변경 실패(비즈니스 에러): userId = {}, error = {}",
				event.userId(), e.getMessage(), e);
			acknowledgment.acknowledge();
		} catch (Exception e) {
			log.warn("[USER_SETTING_LISTENER] 메이트 매칭 가능 상태 false 재시도(unknown 에러): userId = {}, error = {}",
				event.userId(), e.getMessage(), e);
			throw e;
		}
	}

	@KafkaListener(
		topics = MatchingTopic.MATCHING_MATCHED_TOPIC,
		groupId = "matching-usersetting-group",
		containerFactory = "kafkaListenerContainerFactory"
	)
	public void matchingMatched(@Payload String payload, Acknowledgment acknowledgment) {
		MatchingMatchedEvent event = deserializer.deserialize(payload, MatchingMatchedEvent.class);
		try {
			enabledMatchingSettingUsecase.deactivateMatching(event.hostUserId());
			enabledMatchingSettingUsecase.deactivateMatching(event.mateUserId());
			acknowledgment.acknowledge();
		} catch (BusinessException e) {
			log.warn("[USER_SETTING_LISTENER] 매칭 완료 후 매칭 가능 상태 false 변경 실패(비즈니스 에러): matchingId = {}, error = {}",
				event.matchingId(), e.getMessage(), e);
			acknowledgment.acknowledge();
		} catch (Exception e) {
			log.warn("[USER_SETTING_LISTENER] 매칭 완료 후 매칭 가능 상태 false 재시도(unknown 에러): matchingId = {}, error = {}",
				event.matchingId(), e.getMessage(), e);
			throw e;
		}
	}
}

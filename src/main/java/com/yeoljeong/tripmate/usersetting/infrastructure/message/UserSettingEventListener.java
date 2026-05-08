package com.yeoljeong.tripmate.usersetting.infrastructure.message;

import static com.yeoljeong.tripmate.usersetting.domain.exception.UserSettingErrorCode.USER_SETTING_ALREADY_EXISTS;

import com.yeoljeong.tripmate.common.infrastructure.KafkaPayloadDeserializer;
import com.yeoljeong.tripmate.event.MatchingCreateEvent;
import com.yeoljeong.tripmate.event.UserCreatedEvent;
import com.yeoljeong.tripmate.event.enums.MatchingTopic;
import com.yeoljeong.tripmate.event.enums.UserTopic;
import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.usersetting.application.dto.command.CreateUserSettingCommand;
import com.yeoljeong.tripmate.usersetting.application.dto.command.MatchingCandidateCriteria;
import com.yeoljeong.tripmate.usersetting.application.usecase.CreateUserSettingUsecase;
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

	private final CreateUserSettingUsecase usecase;
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
			usecase.create(CreateUserSettingCommand.of(event.userId(), event.gender()));
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
		try {
			findEnableMatchingUserUsecase.findAllEnableMatchingUser(
				new MatchingCandidateCriteria(
					event.matchingId(),
					event.hostUserId(),
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
}

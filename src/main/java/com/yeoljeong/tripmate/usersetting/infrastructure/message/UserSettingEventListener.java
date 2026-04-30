package com.yeoljeong.tripmate.usersetting.infrastructure.message;

import static com.yeoljeong.tripmate.usersetting.domain.exception.UserSettingErrorCode.USER_SETTING_ALREADY_EXISTS;

import com.yeoljeong.tripmate.event.UserCreatedEvent;
import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.usersetting.application.dto.command.CreateUserSettingCommand;
import com.yeoljeong.tripmate.usersetting.application.usecase.CreateUserSettingUsecase;
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

	@KafkaListener(
		topics = "user-create",
		groupId = "${spring.kafka.consumer.group-id}",
		containerFactory = "kafkaListenerContainerFactory"
	)
	public void create(@Payload UserCreatedEvent event, Acknowledgment acknowledgment) {
		try {
			usecase.create(CreateUserSettingCommand.of(event.userId(), event.gender()));
			acknowledgment.acknowledge();
		} catch (BusinessException e) {
			if (USER_SETTING_ALREADY_EXISTS.equals(e.getErrorCode())) {
				log.warn("[UserSetting] already exists: userId: {}", event.userId());
			} else {
				log.error("[UserSetting] create failed (business error): userId: {}, error: {}"
					, event.userId(), e.getMessage());
				throw e;
			}
			acknowledgment.acknowledge();
		} catch (Exception e) {
			log.warn("[UserSetting] Retry scheduled for userSetting creation (unknown error): userId: {}, error: {}",
				event.userId(), e.getMessage());
			throw e;
		}
	}
}

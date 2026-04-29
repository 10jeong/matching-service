package com.yeoljeong.tripmate.usersetting.infrastructure.message;

import static com.yeoljeong.tripmate.usersetting.domain.exception.UserSettingErrorCode.USER_SETTING_ALREADY_EXISTS;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.usersetting.application.usecase.CreateUserSettingUsecase;
import com.yeoljeong.tripmate.usersetting.infrastructure.dto.CreateUserEvent;
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
	public void create(@Payload CreateUserEvent event, Acknowledgment acknowledgment) {
		try {
			usecase.create(event.toCommand());
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

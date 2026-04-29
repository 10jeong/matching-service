package com.yeoljeong.tripmate.usersetting.infrastructure.message;

import com.yeoljeong.tripmate.usersetting.application.usecase.CreateUserSettingUsecase;
import com.yeoljeong.tripmate.usersetting.infrastructure.dto.CreateUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSettingEventListener {

	private final CreateUserSettingUsecase usecase;

	@KafkaListener(
		topics = "user-create",
		groupId = "${spring.kafka.consumer.group-id}"
	)
	public void create(CreateUserEvent event) {
		usecase.create(event.toCommand());
	}
}

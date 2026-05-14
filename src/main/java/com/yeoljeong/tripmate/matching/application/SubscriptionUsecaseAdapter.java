package com.yeoljeong.tripmate.matching.application;

import com.yeoljeong.tripmate.matching.application.dto.command.UserMatchingCriteriaCommand;
import com.yeoljeong.tripmate.matching.application.external.MatchingEventPort;
import com.yeoljeong.tripmate.matching.application.external.UserGeoStore;
import com.yeoljeong.tripmate.matching.application.usecase.SubscriptionUsecase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@Slf4j
@RequiredArgsConstructor
public class SubscriptionUsecaseAdapter implements SubscriptionUsecase {

	private final SseSubscriptionService sseSubscriptionService;
	private final UserGeoStore userGeoStore;
	private final MatchingEventPort eventPort;

	@Override
	public SseEmitter execute(UserMatchingCriteriaCommand command) {
		eventPort.appendMateSubscribed(command.userId());
		SseEmitter emitter = sseSubscriptionService.subscribe(command);
		userGeoStore.saveUserLocation(command.userId(), command.latitude(), command.longitude());
		return emitter;
	}

	@Override
	public SseEmitter execute(UUID userId) {
		return sseSubscriptionService.subscribe(userId);
	}
}

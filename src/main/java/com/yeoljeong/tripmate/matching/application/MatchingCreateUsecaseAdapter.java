package com.yeoljeong.tripmate.matching.application;

import com.yeoljeong.tripmate.matching.application.dto.command.CreateMatchingCommand;
import com.yeoljeong.tripmate.matching.application.dto.result.MatchingDetailResult;
import com.yeoljeong.tripmate.matching.application.external.MatchingEventPort;
import com.yeoljeong.tripmate.matching.application.external.UserGeoStore;
import com.yeoljeong.tripmate.matching.application.usecase.MatchingCreateUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MatchingCreateUsecaseAdapter implements MatchingCreateUsecase {

	private final MatchingCommandService matchingCommandService;
	private final MatchingEventPort eventPort;
	private final UserGeoStore userGeoStore;

	@Override
	public MatchingDetailResult create(CreateMatchingCommand command) {
		var matching = matchingCommandService.create(command);
		userGeoStore.saveUserLocation(
			command.userId(), command.lat().doubleValue(), command.lng().doubleValue()
		);
		eventPort.appendMatchingCreated(matching);
		log.info("[Matching] matching.created 이벤트 발행 - matchingId: {}", matching.getId());
		return MatchingDetailResult.from(matching);
	}
}

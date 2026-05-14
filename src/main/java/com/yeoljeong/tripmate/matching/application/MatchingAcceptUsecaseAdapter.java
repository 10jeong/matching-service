package com.yeoljeong.tripmate.matching.application;

import com.yeoljeong.tripmate.matching.application.external.MatchingEventPort;
import com.yeoljeong.tripmate.matching.application.external.UserGeoStore;
import com.yeoljeong.tripmate.matching.application.usecase.MatchingAcceptUsecase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatchingAcceptUsecaseAdapter implements MatchingAcceptUsecase {

	private final MatchingEventPort matchingEventPort;
	private final MatchingCommandService matchingCommandService;
	private final UserGeoStore userGeoStore;

	@Override
	public void accept(UUID userId, UUID matchingId) {
		var matching = matchingCommandService.accept(userId, matchingId);
		userGeoStore.removeUserLocation(matching.getHostUserId());
		matchingEventPort.appendMatchingAccomplished(matching);
	}
}

package com.yeoljeong.tripmate.usersetting.application;

import com.yeoljeong.tripmate.usersetting.application.dto.command.MatchingCandidateCriteria;
import com.yeoljeong.tripmate.usersetting.application.external.UserSettingEventPort;
import com.yeoljeong.tripmate.usersetting.application.usecase.FindEnableMatchingUserUsecase;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FindEnableMatchingUserUsecaseAdapter implements FindEnableMatchingUserUsecase {

	private final UserSettingCommandService userSettingCommandService;
	private final UserSettingQueryService userSettingQueryService;
	private final NearbyUserFiltering nearbyUserFiltering;
	private final UserSettingEventPort userSettingEventPort;

	@Override
	public void findAllEnableMatchingUser(MatchingCandidateCriteria criteria) {
		userSettingCommandService.activateMatching(criteria.hostUserId());
		Set<UUID> nearbyUsers = nearbyUserFiltering.findNearbyUsers(criteria.latitude(),
			criteria.longitude(), 1.0);

		if (nearbyUsers.isEmpty()) {
			userSettingEventPort.appendCandidateFound(criteria.matchingId(), criteria.hostUserId(), List.of());
			return;
		}
		List<UUID> candidatesId = userSettingQueryService.findAllEnableMatchingUser(
			criteria, nearbyUsers);
		log.info("[UserSetting] 후보군 수: {}", candidatesId.size());
		userSettingEventPort.appendCandidateFound(criteria.matchingId(), criteria.hostUserId(), candidatesId);
	}
}

package com.yeoljeong.tripmate.usersetting.application;

import com.yeoljeong.tripmate.usersetting.application.dto.command.MatchingCandidateCriteria;
import com.yeoljeong.tripmate.usersetting.application.external.UserSettingEventPort;
import com.yeoljeong.tripmate.usersetting.application.usecase.FindEnableMatchingUserUsecase;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindEnableMatchingUserUsecaseAdapter implements FindEnableMatchingUserUsecase {

	private final UserSettingCommandService userSettingCommandService;
	private final UserSettingQueryService userSettingQueryService;
	private final UserSettingEventPort userSettingEventPort;

	@Override
	public void findAllEnableMatchingUser(MatchingCandidateCriteria criteria) {
		userSettingCommandService.activateMatching(criteria.hostUserId());
		List<UUID> candidatesId = userSettingQueryService.findAllEnableMatchingUser(
			criteria);
		userSettingEventPort.appendCandidateFound(criteria.matchingId(), criteria.hostUserId(), candidatesId);
	}
}

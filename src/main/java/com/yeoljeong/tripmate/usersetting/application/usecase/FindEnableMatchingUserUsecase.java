package com.yeoljeong.tripmate.usersetting.application.usecase;

import com.yeoljeong.tripmate.usersetting.application.dto.command.MatchingCandidateCriteria;

public interface FindEnableMatchingUserUsecase {
	void findAllEnableMatchingUser(MatchingCandidateCriteria criteria);
}

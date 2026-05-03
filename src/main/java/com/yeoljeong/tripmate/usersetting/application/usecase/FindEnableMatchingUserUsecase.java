package com.yeoljeong.tripmate.usersetting.application.usecase;

import com.yeoljeong.tripmate.usersetting.application.dto.command.MatchingCandidateCriteria;
import java.security.NoSuchAlgorithmException;

public interface FindEnableMatchingUserUsecase {
	void findAllEnableMatchingUser(MatchingCandidateCriteria criteria)
		throws NoSuchAlgorithmException;
}

package com.yeoljeong.tripmate.usersetting.application.usecase;

import com.yeoljeong.tripmate.usersetting.application.dto.command.MatchingCandidateCriteria;
import java.util.List;
import java.util.UUID;

public interface FindEnableMatchingUserUsecase {
	List<UUID> findAllEnableMatchingUser(MatchingCandidateCriteria criteria);
}

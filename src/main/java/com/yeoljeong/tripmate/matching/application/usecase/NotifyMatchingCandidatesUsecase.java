package com.yeoljeong.tripmate.matching.application.usecase;

import com.yeoljeong.tripmate.matching.application.dto.command.NotifyMatchingCommand;

public interface NotifyMatchingCandidatesUsecase {
	void sendMatchingInfo(NotifyMatchingCommand command);
}

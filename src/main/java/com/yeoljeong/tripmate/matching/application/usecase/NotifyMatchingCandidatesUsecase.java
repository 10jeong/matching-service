package com.yeoljeong.tripmate.matching.application.usecase;

import com.yeoljeong.tripmate.matching.application.dto.command.NotifyMatchingCommand;
import java.util.UUID;

public interface NotifyMatchingCandidatesUsecase {
	void sendMatchingInfo(NotifyMatchingCommand command);
	void sendMatchingAccomplished(UUID matchingId);
}

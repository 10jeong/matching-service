package com.yeoljeong.tripmate.matching.application.usecase;

import com.yeoljeong.tripmate.matching.application.dto.command.CreateMatchingCommand;
import com.yeoljeong.tripmate.matching.application.dto.result.MatchingDetailResult;

public interface MatchingCreateUsecase {
	MatchingDetailResult create(CreateMatchingCommand command);
}

package com.yeoljeong.tripmate.matching.presentation.dto.request;

import com.yeoljeong.tripmate.matching.application.dto.command.CreateMatchingCommand;
import com.yeoljeong.tripmate.matching.domain.constants.MatchingStatus;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiIE;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiPJ;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiSN;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiTF;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateMatchingRequest(
	UUID id,
	UUID hostUserId,
	String title,
	BigDecimal lat,
	BigDecimal lng,
	LocalDateTime scheduledAt,
	LocalDateTime recruitedAt,
	MatchingStatus status,
	PreferenceMbtiIE ie,
	PreferenceMbtiSN sn,
	PreferenceMbtiTF tf,
	PreferenceMbtiPJ pj
) {

	public CreateMatchingCommand toCommand(UUID userId) {
		return new CreateMatchingCommand();
	}
}

package com.yeoljeong.tripmate.usersetting.application.dto.command;

import java.util.UUID;

public record MatchingCandidateCriteria(
	UUID matchingId,
	UUID hostUserId,
	Double latitude,
	Double longitude,
	boolean allowSmoking,
	String preferenceMbtiIE,
	String preferenceMbtiSN,
	String preferenceMbtiTF,
	String preferenceMbtiPJ,
	String preferenceGender
) {
}

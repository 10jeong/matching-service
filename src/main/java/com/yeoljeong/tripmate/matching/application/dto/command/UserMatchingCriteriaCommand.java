package com.yeoljeong.tripmate.matching.application.dto.command;

import java.util.UUID;

public record UserMatchingCriteriaCommand(
	UUID userId,
	Double latitude,
	Double longitude
) {
}

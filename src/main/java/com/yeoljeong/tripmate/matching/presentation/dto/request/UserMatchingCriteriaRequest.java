package com.yeoljeong.tripmate.matching.presentation.dto.request;

import com.yeoljeong.tripmate.matching.application.dto.command.UserMatchingCriteriaCommand;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UserMatchingCriteriaRequest (
	@NotNull
	Double lat,
	@NotNull
	Double lng
){

	public UserMatchingCriteriaCommand toCommand(UUID userId) {
		return new UserMatchingCriteriaCommand(userId, lat, lng);
	}
}

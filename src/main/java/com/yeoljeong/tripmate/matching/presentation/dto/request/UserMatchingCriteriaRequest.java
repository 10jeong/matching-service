package com.yeoljeong.tripmate.matching.presentation.dto.request;

import com.yeoljeong.tripmate.matching.application.dto.command.UserMatchingCriteriaCommand;
import java.util.UUID;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record UserMatchingCriteriaRequest (
	String gender,
	boolean isSmoking,
	@DefaultValue(value = "UNKNOWN")
	String mbtiIE,
	@DefaultValue(value = "UNKNOWN")
	String mbtiSN,
	@DefaultValue(value = "UNKNOWN")
	String mbtiTF,
	@DefaultValue(value = "UNKNOWN")
	String mbtiPJ
){

	public UserMatchingCriteriaCommand toCommand(UUID userId) {
		return new UserMatchingCriteriaCommand(
			userId,
			gender,
			isSmoking,
			mbtiIE,
			mbtiSN,
			mbtiTF,
			mbtiPJ
		);
	}
}

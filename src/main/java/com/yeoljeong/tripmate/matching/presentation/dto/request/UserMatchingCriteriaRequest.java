package com.yeoljeong.tripmate.matching.presentation.dto.request;

import com.yeoljeong.tripmate.matching.application.dto.command.UserMatchingCriteriaCommand;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UserMatchingCriteriaRequest (
	String gender,
	boolean isSmoking,
	@NotNull
	Double latitude,
	@NotNull
	Double longitude,
	String mbtiIE,
	String mbtiSN,
	String mbtiTF,
	String mbtiPJ
){
	public UserMatchingCriteriaRequest {
		mbtiIE = mbtiIE == null ? "UNKNOWN" : mbtiIE;
		mbtiSN = mbtiSN == null ? "UNKNOWN" : mbtiSN;
		mbtiTF = mbtiTF == null ? "UNKNOWN" : mbtiTF;
		mbtiPJ = mbtiPJ == null ? "UNKNOWN" : mbtiPJ;
	}

	public UserMatchingCriteriaCommand toCommand(UUID userId) {
		return new UserMatchingCriteriaCommand(
			userId,
			gender,
			isSmoking,
			latitude,
			longitude,
			mbtiIE,
			mbtiSN,
			mbtiTF,
			mbtiPJ
		);
	}
}

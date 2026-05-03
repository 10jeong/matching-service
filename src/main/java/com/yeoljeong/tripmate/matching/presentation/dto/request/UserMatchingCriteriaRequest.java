package com.yeoljeong.tripmate.matching.presentation.dto.request;

import com.yeoljeong.tripmate.matching.application.dto.command.UserMatchingCriteriaCommand;
import java.util.UUID;

public record UserMatchingCriteriaRequest (
	String gender,
	boolean isSmoking,
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
			mbtiIE,
			mbtiSN,
			mbtiTF,
			mbtiPJ
		);
	}
}

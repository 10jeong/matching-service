package com.yeoljeong.tripmate.matching.infrastructure.feign.dto;

import java.util.UUID;

public record UserSettingResponse (
	UUID userId,
	String gender,
	boolean isSmoking,
	String mbtiIE,
	String mbtiSN,
	String mbtiTF,
	String mbtiPJ
) {
	public static UserSettingResponse of(
		UUID userId,
		String gender,
		boolean isSmoking,
		String mbtiIE,
		String mbtiSN,
		String mbtiTF,
		String mbtiPJ
	) {
		return new UserSettingResponse(
			userId, gender, isSmoking, mbtiIE, mbtiSN, mbtiTF, mbtiPJ
		);
	}
}

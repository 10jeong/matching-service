package com.yeoljeong.tripmate.usersetting.presentation.dto.response;

import com.yeoljeong.tripmate.usersetting.application.dto.result.UserSettingResult;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiTF;
import java.util.UUID;

public record UserSettingResponse(
	UUID userId,
	String gender,
	boolean isSmoking,
	MbtiIE mbtiIE,
	MbtiSN mbtiSN,
	MbtiTF mbtiTF,
	MbtiPJ mbtiPJ
) {
	public static UserSettingResponse from(UserSettingResult result) {
		return new UserSettingResponse(
			result.userId(),
			result.gender().name(),
			result.isSmoking(),
			result.mbtiIE(),
			result.mbtiSN(),
			result.mbtiTF(),
			result.mbtiPJ()
		);
	}
}
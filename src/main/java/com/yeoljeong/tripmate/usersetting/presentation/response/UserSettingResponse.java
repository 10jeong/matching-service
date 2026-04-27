package com.yeoljeong.tripmate.usersetting.presentation.response;

import com.yeoljeong.tripmate.usersetting.application.result.UserSettingResult;
import java.util.UUID;

public record UserSettingResponse(
	UUID userId,
	String gender,
	boolean isSmoking,
	MbtiResponse mbti
) {
	public static UserSettingResponse from(UserSettingResult result) {
		return new UserSettingResponse(
			result.userId(),
			result.gender().name(),
			result.isSmoking(),
			MbtiResponse.from(result.mbti())
		);
	}
}
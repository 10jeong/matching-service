package com.yeoljeong.tripmate.usersetting.application.dto.result;

import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.Gender;
import java.util.UUID;

public record UserSettingResult (
	UUID userId,
	Gender gender,
	boolean isSmoking,
	MbtiResult mbti
){
	public static UserSettingResult from(UserSetting setting) {
		return new UserSettingResult(
			setting.getUserId(),
			setting.getGender(),
			setting.isSmoking(),
			MbtiResult.from(setting)
		);
	}
}

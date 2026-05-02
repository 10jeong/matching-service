package com.yeoljeong.tripmate.usersetting.application.dto.result;

import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.Gender;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiTF;
import java.util.UUID;

public record UserSettingResult (
	UUID userId,
	Gender gender,
	boolean isSmoking,
	MbtiIE mbtiIE,
	MbtiSN mbtiSN,
	MbtiTF mbtiTF,
	MbtiPJ mbtiPJ
){
	public static UserSettingResult from(UserSetting setting) {
		return new UserSettingResult(
			setting.getUserId(),
			setting.getGender(),
			setting.isSmoking(),
			setting.getIe(),
			setting.getSn(),
			setting.getTf(),
			setting.getPj()
		);
	}
}

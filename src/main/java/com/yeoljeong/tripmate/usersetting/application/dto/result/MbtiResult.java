package com.yeoljeong.tripmate.usersetting.application.dto.result;

import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiTF;

public record MbtiResult(
	MbtiIE ie,
	MbtiSN sn,
	MbtiTF tf,
	MbtiPJ pj
) {
	public static MbtiResult from(UserSetting setting) {
		return new MbtiResult(setting.getIe(), setting.getSn(), setting.getTf(), setting.getPj());
	}
}

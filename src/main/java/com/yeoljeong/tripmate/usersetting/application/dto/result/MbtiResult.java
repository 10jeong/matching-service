package com.yeoljeong.tripmate.usersetting.application.dto.result;

import com.yeoljeong.tripmate.usersetting.domain.entity.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiTF;

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

package com.yeoljeong.tripmate.usersetting.application.dto.result;

import com.yeoljeong.tripmate.usersetting.domain.entity.UserSetting;

public record MbtiResult(
	String ie,
	String sn,
	String tf,
	String pj
) {
	public static MbtiResult from(UserSetting setting) {
		return new MbtiResult(
			setting.getIe().name(),
			setting.getSn().name(),
			setting.getTf().name(),
			setting.getPj().name()
		);
	}
}

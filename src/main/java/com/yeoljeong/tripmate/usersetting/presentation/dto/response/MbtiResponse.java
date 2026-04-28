package com.yeoljeong.tripmate.usersetting.presentation.dto.response;

import com.yeoljeong.tripmate.usersetting.application.dto.result.MbtiResult;

public record MbtiResponse(
	String ie,
	String sn,
	String tf,
	String pj
) {
	public static MbtiResponse from(MbtiResult mbti) {
		return new MbtiResponse(
			mbti.ie().name(),
			mbti.sn().name(),
			mbti.tf().name(),
			mbti.pj().name()
		);
	}
}


package com.yeoljeong.tripmate.matching.presentation.dto.response;

import com.yeoljeong.tripmate.matching.application.dto.result.MatchingDetailResult;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceGender;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiIE;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiPJ;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiSN;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiTF;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MatchingDetailResponse (
	UUID id,
	String title,
	String description,
	BigDecimal lat,
	BigDecimal lng,
	LocalDateTime scheduledAt,
	LocalDateTime recruitDeadline,
	String chatUrl,
	PreferenceMbtiIE ie,
	PreferenceMbtiSN sn,
	PreferenceMbtiTF tf,
	PreferenceMbtiPJ pj,
	PreferenceGender gender,
	Boolean allowSmoking
){
	public static MatchingDetailResponse from(MatchingDetailResult result) {
		return new MatchingDetailResponse(
			result.id(),
			result.title(),
			result.description(),
			result.location().getLat(), result.location().getLng(),
			result.period().getScheduledAt(), result.period().getRecruitDeadline(),
			result.chatUrl(),
			result.setting().getPreferenceMbtiIE(),
			result.setting().getPreferenceMbtiSN(),
			result.setting().getPreferenceMbtiTF(),
			result.setting().getPreferenceMbtiPJ(),
			result.setting().getPreferenceGender(),
			result.setting().isAllowSmoking()
		);
	}
}

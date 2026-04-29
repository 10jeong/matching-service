package com.yeoljeong.tripmate.matching.application.dto.command;

import com.yeoljeong.tripmate.matching.domain.constants.PreferenceGender;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiIE;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiPJ;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiSN;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiTF;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateMatchingCommand (
	UUID userId,
	String title,
	String description,
	BigDecimal lat,
	BigDecimal lng,
	LocalDateTime scheduledAt,
	LocalDateTime recruitedAt,
	String chatUrl,
	PreferenceMbtiIE ie,
	PreferenceMbtiSN sn,
	PreferenceMbtiTF tf,
	PreferenceMbtiPJ pj,
	PreferenceGender preferenceGender,
	Boolean allowSmoking
) {

}

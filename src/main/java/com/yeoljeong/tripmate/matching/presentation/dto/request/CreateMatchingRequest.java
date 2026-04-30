package com.yeoljeong.tripmate.matching.presentation.dto.request;

import com.yeoljeong.tripmate.matching.application.dto.command.CreateMatchingCommand;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceGender;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiIE;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiPJ;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiSN;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiTF;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.validator.constraints.Length;

public record CreateMatchingRequest(
	@NotNull
	@Length(min = 4, max = 50)
	String title,
	@Length(max = 500)
	String description,
	@NotNull
	BigDecimal lat,
	@NotNull
	BigDecimal lng,
	@NotNull
	LocalDateTime scheduledAt,
	@NotNull
	LocalDateTime recruitedAt,
	String chatUrl,
	PreferenceMbtiIE ie,
	PreferenceMbtiSN sn,
	PreferenceMbtiTF tf,
	PreferenceMbtiPJ pj,
	PreferenceGender preferenceGender,
	Boolean allowSmoking
) {

	public CreateMatchingCommand toCommand(UUID userId) {
		return new CreateMatchingCommand(
			userId,
			title,
			description,
			lat,
			lng,
			scheduledAt,
			recruitedAt,
			chatUrl,
			ie,
			sn,
			tf,
			pj,
			preferenceGender,
			allowSmoking
		);
	}
}

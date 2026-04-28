package com.yeoljeong.tripmate.usersetting.presentation.dto.request;

import com.yeoljeong.tripmate.usersetting.application.dto.command.UserSettingCommand;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiTF;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UserSettingUpdateRequest(
	@NotNull(message = "흡연 여부는 null 일 수 없습니다.")
	Boolean isSmoking,
	MbtiIE ie,
	MbtiSN sn,
	MbtiTF tf,
	MbtiPJ pj
) {
	public UserSettingCommand toCommand(UUID userId) {
		return new UserSettingCommand(userId, isSmoking, ie, sn, tf, pj);
	}
}

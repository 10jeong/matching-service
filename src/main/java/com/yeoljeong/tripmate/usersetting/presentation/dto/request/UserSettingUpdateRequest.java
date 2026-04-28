package com.yeoljeong.tripmate.usersetting.presentation.dto.request;

import com.yeoljeong.tripmate.usersetting.application.dto.command.UserSettingCommand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public record UserSettingUpdateRequest(
	@NotNull(message = "흡연 여부는 null 일 수 없습니다.")
	Boolean isSmoking,
	@Pattern(regexp = "[IE]", message = "I 또는 E만 입력 가능합니다.")
	String ie,
	@Pattern(regexp = "[SN]", message = "S 또는 N만 입력 가능합니다.")
	String sn,
	@Pattern(regexp = "[TF]", message = "T 또는 F만 입력 가능합니다.")
	String tf,
	@Pattern(regexp = "[PJ]", message = "P 또는 J만 입력 가능합니다.")
	String pj
) {
	public UserSettingCommand toCommand(UUID userId) {
		return new UserSettingCommand(userId, isSmoking, ie, sn, tf, pj);
	}
}

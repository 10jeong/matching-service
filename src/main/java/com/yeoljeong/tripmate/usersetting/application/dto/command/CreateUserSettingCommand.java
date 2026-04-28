package com.yeoljeong.tripmate.usersetting.application.dto.command;

import java.util.UUID;

public record CreateUserSettingCommand(
	UUID userId,
	String gender
) {
	public static CreateUserSettingCommand of(UUID userId, String gender) {
		return new CreateUserSettingCommand(userId, gender);
	}
}

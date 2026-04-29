package com.yeoljeong.tripmate.usersetting.infrastructure.dto;

import com.yeoljeong.tripmate.usersetting.application.dto.command.CreateUserSettingCommand;
import java.util.UUID;

public record CreateUserEvent (
	UUID userId,
	String gender
) {
	public CreateUserSettingCommand toCommand() {
		return CreateUserSettingCommand.of(userId, gender);
	}
}

package com.yeoljeong.tripmate.usersetting.application.usecase;

import com.yeoljeong.tripmate.usersetting.application.dto.command.CreateUserSettingCommand;

public interface CreateUserSettingUsecase {
	void create(CreateUserSettingCommand command);
}

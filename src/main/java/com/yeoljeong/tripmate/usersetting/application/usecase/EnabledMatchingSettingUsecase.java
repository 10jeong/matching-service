package com.yeoljeong.tripmate.usersetting.application.usecase;

import java.util.UUID;

public interface EnabledMatchingSettingUsecase {
	void activateMatching(UUID userId);
	void deactivateMatching(UUID userId);
}

package com.yeoljeong.tripmate.usersetting.domain.repository;

import com.yeoljeong.tripmate.usersetting.domain.entity.UserSetting;
import java.util.Optional;
import java.util.UUID;

public interface UserSettingRepository {
	Optional<UserSetting> findByUserIdAndIsDeletedFalse(UUID userId);
}

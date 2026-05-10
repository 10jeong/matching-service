package com.yeoljeong.tripmate.usersetting.domain.repository;

import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSettingRepository {
	Optional<UserSetting> findByUserIdAndIsDeletedFalse(UUID userId);
	boolean existsByUserIdAndIsDeletedFalse(UUID userId);
	UserSetting save(UserSetting userSetting);
	List<UserSetting> findCandidateByCriteria(UUID hostUserId, String gender, boolean allowSmoking,
		String mbtiIE, String mbtiSN, String mbtiTF, String mbtiPJ);
}

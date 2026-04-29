package com.yeoljeong.tripmate.usersetting.infrastructure.persistence.repositoryimpl;

import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.repository.UserSettingRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserSettingRepositoryImpl implements UserSettingRepository {

	private final com.yeoljeong.tripmate.usersetting.infrastructure.persistence.jpa.UserSettingJpaRepository userSettingRepository;

	@Override
	public Optional<UserSetting> findByUserIdAndIsDeletedFalse(UUID userId) {
		return userSettingRepository.findByUserIdAndIsDeletedFalse(userId);
	}

	@Override
	public boolean existsByUserIdAndIsDeletedFalse(UUID userId) {
		return userSettingRepository.existsByUserIdAndIsDeletedFalse(userId);
	}

	@Override
	public UserSetting save(UserSetting userSetting) {
		return userSettingRepository.save(userSetting);
	}
}


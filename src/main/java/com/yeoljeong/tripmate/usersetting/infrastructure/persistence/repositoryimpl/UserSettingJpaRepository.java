package com.yeoljeong.tripmate.usersetting.infrastructure.persistence.repositoryimpl;

import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.repository.UserSettingRepository;
import com.yeoljeong.tripmate.usersetting.infrastructure.persistence.jpa.SpringDataUserSettingRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserSettingJpaRepository implements UserSettingRepository {

	private final SpringDataUserSettingRepository userSettingRepository;

	@Override
	public Optional<UserSetting> findByUserIdAndIsDeletedFalse(UUID userId) {
		return userSettingRepository.findByUserIdAndIsDeletedFalse(userId);
	}
}


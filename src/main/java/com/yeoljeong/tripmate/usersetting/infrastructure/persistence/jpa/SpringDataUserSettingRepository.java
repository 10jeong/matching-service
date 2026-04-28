package com.yeoljeong.tripmate.usersetting.infrastructure.persistence.jpa;

import com.yeoljeong.tripmate.usersetting.domain.entity.UserSetting;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserSettingRepository extends JpaRepository<UserSetting, UUID> {
	Optional<UserSetting> findByUserIdAndIsDeletedFalse(UUID userId);
}

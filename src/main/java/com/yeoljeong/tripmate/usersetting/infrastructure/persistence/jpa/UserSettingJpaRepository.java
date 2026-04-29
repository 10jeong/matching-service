package com.yeoljeong.tripmate.usersetting.infrastructure.persistence.jpa;

import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingJpaRepository extends JpaRepository<UserSetting, UUID> {
	Optional<UserSetting> findByUserIdAndIsDeletedFalse(UUID userId);
	boolean existsByUserIdAndIsDeletedFalse(UUID userId);
}

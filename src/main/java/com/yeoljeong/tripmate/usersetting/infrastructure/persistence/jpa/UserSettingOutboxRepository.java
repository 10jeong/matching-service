package com.yeoljeong.tripmate.usersetting.infrastructure.persistence.jpa;

import com.yeoljeong.tripmate.domain.constants.OutboxStatus;
import com.yeoljeong.tripmate.usersetting.infrastructure.persistence.UserSettingOutbox;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingOutboxRepository extends JpaRepository<UserSettingOutbox, UUID> {
	List<UserSettingOutbox> findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus status);
}

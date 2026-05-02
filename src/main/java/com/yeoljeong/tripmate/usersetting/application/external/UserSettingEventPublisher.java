package com.yeoljeong.tripmate.usersetting.application.external;

import java.util.List;
import java.util.UUID;

public interface UserSettingEventPublisher {
	void publishMatchingCandidates(UUID hostUserId, List<UUID> candidates);
}

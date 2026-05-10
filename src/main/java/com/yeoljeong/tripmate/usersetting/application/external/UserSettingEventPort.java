package com.yeoljeong.tripmate.usersetting.application.external;

import java.util.List;
import java.util.UUID;

public interface UserSettingEventPort {
	void appendCandidateFound(UUID matchingId, UUID hostUserId, List<UUID> candidates);
}

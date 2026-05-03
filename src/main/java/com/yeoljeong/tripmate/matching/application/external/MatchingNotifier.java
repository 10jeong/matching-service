package com.yeoljeong.tripmate.matching.application.external;

import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.util.List;
import java.util.UUID;

public interface MatchingNotifier {
	void publishToUsers(List<UUID> userIds, Matching matching);
	void publishToUserDirect(UUID userId, Matching matching);
}

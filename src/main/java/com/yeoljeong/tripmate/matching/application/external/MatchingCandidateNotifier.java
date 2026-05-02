package com.yeoljeong.tripmate.matching.application.external;

import com.yeoljeong.tripmate.matching.domain.model.Matching;
import java.util.List;
import java.util.UUID;

public interface MatchingCandidateNotifier {
	void publishToUsers(List<UUID> userIds, Matching matching);
}

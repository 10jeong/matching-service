package com.yeoljeong.tripmate.common.event;

import java.util.List;
import java.util.UUID;

public record MatchingCandidatesFoundEvent (
	UUID hostUserId,
	List<UUID> userIds
) {
}

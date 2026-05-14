package com.yeoljeong.tripmate.common.message;

import java.util.UUID;

public record MatchingMatchedPayload(
	UUID matchingId,
	UUID hostUserId,
	UUID mateUserId,
	String chatUrl,
	String title,
	String description
) {

}

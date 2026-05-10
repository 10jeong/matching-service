package com.yeoljeong.tripmate.common.message;

import java.util.UUID;

public record MatchingSsePayload (
	UUID matchingId,
	UUID hostUserId,
	String title,
	String description
){

}

package com.yeoljeong.tripmate.matching.application.dto.command;

import java.util.List;
import java.util.UUID;

public record NotifyMatchingCommand (
	UUID hostUserId,
	List<UUID> userIds
){

}

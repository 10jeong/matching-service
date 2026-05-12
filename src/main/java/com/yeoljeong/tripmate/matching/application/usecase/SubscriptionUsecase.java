package com.yeoljeong.tripmate.matching.application.usecase;

import com.yeoljeong.tripmate.matching.application.dto.command.UserMatchingCriteriaCommand;
import java.util.UUID;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SubscriptionUsecase {

	SseEmitter execute(UserMatchingCriteriaCommand command);
	SseEmitter execute(UUID userId);

}

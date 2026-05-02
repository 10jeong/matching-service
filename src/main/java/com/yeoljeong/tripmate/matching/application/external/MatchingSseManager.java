package com.yeoljeong.tripmate.matching.application.external;

import java.util.UUID;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface MatchingSseManager {
	SseEmitter subscribe(UUID userId);
	void disconnect(UUID userId);
}

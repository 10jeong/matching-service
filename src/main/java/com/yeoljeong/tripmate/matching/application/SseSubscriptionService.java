package com.yeoljeong.tripmate.matching.application;

import com.yeoljeong.tripmate.matching.application.dto.command.UserMatchingCriteriaCommand;
import com.yeoljeong.tripmate.matching.application.external.MatchingNotifier;
import com.yeoljeong.tripmate.matching.application.external.MatchingSseManager;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class SseSubscriptionService {

	private final MatchingSseManager sseManager;
	private final MatchingRepository repository;
	private final MatchingNotifier candidateNotifier;

	@Transactional(readOnly = true)
	public SseEmitter subscribe(UserMatchingCriteriaCommand command) {
		SseEmitter emitter = sseManager.subscribe(command.userId());

		List<Matching> openMatchings = repository.findAllByCriteria(
			command.userId(), command.gender(), command.smoking(),
			command.mbtiIE(), command.mbtiSN(), command.mbtiTF(), command.mbtiPJ()
		);

		openMatchings.forEach(
			matching -> candidateNotifier.publishToUserDirect(command.userId(), matching)
		);
		return emitter;
	}
}

package com.yeoljeong.tripmate.matching.application;

import com.yeoljeong.tripmate.matching.application.dto.command.UserMatchingCriteriaCommand;
import com.yeoljeong.tripmate.matching.application.dto.result.UserSettingInfo;
import com.yeoljeong.tripmate.matching.application.external.MatchingNotifier;
import com.yeoljeong.tripmate.matching.application.external.MatchingSseManager;
import com.yeoljeong.tripmate.matching.application.external.UserSettingPort;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
public class SseSubscriptionService {

	private final MatchingSseManager sseManager;
	private final MatchingRepository repository;
	private final MatchingNotifier candidateNotifier;
	private final UserSettingPort userSettingPort;
	private final NearbyUserFiltering nearbyUserFiltering;

	public SseSubscriptionService(
		MatchingSseManager sseManager,
		MatchingRepository repository,
		MatchingNotifier candidateNotifier,
		UserSettingPort userSettingPort,
		@Qualifier("matchingNearbyFiltering") NearbyUserFiltering nearbyUserFiltering
	) {
		this.sseManager = sseManager;
		this.repository = repository;
		this.candidateNotifier = candidateNotifier;
		this.userSettingPort = userSettingPort;
		this.nearbyUserFiltering = nearbyUserFiltering;
	}


	@Transactional(readOnly = true)
	public SseEmitter subscribe(UserMatchingCriteriaCommand command) {
		SseEmitter emitter = sseManager.subscribe(command.userId());
		UserSettingInfo setting = userSettingPort.getUserSetting(command.userId());
		Set<UUID> nearbyUsers = nearbyUserFiltering.findNearbyUsers(
			command.latitude(), command.longitude(), 1.0
		);
		log.info("[SSE] 반경 내 유저: {}", nearbyUsers);
		List<Matching> openMatchings = repository.findAllByCriteria(
			setting.userId(),
			setting.gender(),
			setting.isSmoking(),
			setting.mbtiIE(),
			setting.mbtiSN(),
			setting.mbtiTF(),
			setting.mbtiPJ()
		);
		log.info("[SSE] OPEN 매칭방: {}", openMatchings.size());

		openMatchings.stream()
			.filter(matching -> {
				boolean contains = nearbyUsers.contains(matching.getHostUserId());
				log.info("[SSE] hostUserId: {}, nearbyUsers에 포함: {}", matching.getHostUserId(), contains);
				return contains;
			})
			.forEach(matching -> candidateNotifier.publishToUserDirect(command.userId(), matching));
		return emitter;
	}

	@Transactional(readOnly = true)
	public SseEmitter subscribe(UUID userId) {
		return sseManager.subscribe(userId);
	}
}

package com.yeoljeong.tripmate.matching.application;

import static com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode.NO_ACTIVE_MATCHING;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.matching.application.dto.command.NotifyMatchingCommand;
import com.yeoljeong.tripmate.matching.application.external.MatchingNotifier;
import com.yeoljeong.tripmate.matching.application.usecase.NotifyMatchingCandidatesUsecase;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchingNotificationService implements NotifyMatchingCandidatesUsecase {

	private final MatchingRepository repository;
	private final MatchingNotifier matchingNotifier;

	@Override
	public void sendMatchingInfo(NotifyMatchingCommand command) {
		Matching matching = repository.findByHostUserIdAndMatchingStatusOpen(command.hostUserId())
			.orElseThrow(() -> new BusinessException(NO_ACTIVE_MATCHING));

		matchingNotifier.publishToUsers(command.userIds(), matching);
	}
}

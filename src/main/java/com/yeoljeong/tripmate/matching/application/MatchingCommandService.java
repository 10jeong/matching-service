package com.yeoljeong.tripmate.matching.application;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.matching.application.dto.command.CreateMatchingCommand;
import com.yeoljeong.tripmate.matching.application.dto.result.MatchingDetailResult;
import com.yeoljeong.tripmate.matching.domain.exception.MatchingErrorCode;
import com.yeoljeong.tripmate.matching.domain.model.Location;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import com.yeoljeong.tripmate.matching.domain.model.MatchingPeriod;
import com.yeoljeong.tripmate.matching.domain.model.MatchingSetting;
import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingCommandService {

	private final MatchingRepository repository;

	public MatchingDetailResult create(CreateMatchingCommand command) {
		if (repository.existsByHostUserIdAndMatchingStatusOpen(command.userId())) {
			throw new BusinessException(MatchingErrorCode.MATCHING_ALREADY_IN_PROGRESS);
		}
		Matching matching = Matching.create(
			command.userId(),
			command.title(),
			command.description(),
			Location.of(command.lat(), command.lng()),
			MatchingPeriod.of(command.scheduledAt(), command.recruitedAt()),
			command.chatUrl(),
			MatchingSetting.of(
				command.ie(), command.sn(), command.tf(), command.pj(), command.preferenceGender(), command.allowSmoking()
			)
		);

		return MatchingDetailResult.from(repository.save(matching));
	}
}

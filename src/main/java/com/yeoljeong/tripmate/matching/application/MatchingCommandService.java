package com.yeoljeong.tripmate.matching.application;

import com.yeoljeong.tripmate.matching.application.dto.command.CreateMatchingCommand;
import com.yeoljeong.tripmate.matching.application.dto.result.MatchingDetailResult;
import com.yeoljeong.tripmate.matching.domain.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingCommandService {

	private final MatchingRepository matchingRepository;

	public MatchingDetailResult create(CreateMatchingCommand command) {
		return null;
	}
}

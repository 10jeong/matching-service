package com.yeoljeong.tripmate.usersetting.application;

import static com.yeoljeong.tripmate.usersetting.domain.exception.UserSettingErrorCode.NOT_FOUND_USER_SETTING;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.usersetting.application.dto.command.MatchingCandidateCriteria;
import com.yeoljeong.tripmate.usersetting.application.dto.result.UserSettingResult;
import com.yeoljeong.tripmate.usersetting.application.external.UserSettingEventPublisher;
import com.yeoljeong.tripmate.usersetting.application.usecase.FindEnableMatchingUserUsecase;
import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.repository.UserSettingRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserSettingQueryService implements FindEnableMatchingUserUsecase {

	private final UserSettingRepository userSettingRepository;
	private final UserSettingEventPublisher publisher;

	public UserSettingResult getOne(UUID userId) {
		UserSetting setting = userSettingRepository.findByUserIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_USER_SETTING));
		return UserSettingResult.from(setting);
	}

	@Override
	public void findAllEnableMatchingUser(MatchingCandidateCriteria criteria) {
		List<UUID> candidates = userSettingRepository.findAllByMatchingEnabledTrueIsDeletedFalse()
			.stream()
			.map(UserSetting::getUserId)
			.filter(userId -> !userId.equals(criteria.hostUserId()))
			.toList();
		log.info("[UserSetting] 후보 유저 수: {}", candidates.size()); // 추가
		publisher.publishMatchingCandidates(criteria.hostUserId(), candidates);
	}
}

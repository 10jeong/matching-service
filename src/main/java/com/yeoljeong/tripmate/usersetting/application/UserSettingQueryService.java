package com.yeoljeong.tripmate.usersetting.application;

import static com.yeoljeong.tripmate.usersetting.domain.exception.UserSettingErrorCode.NOT_FOUND_USER_SETTING;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.usersetting.application.dto.command.MatchingCandidateCriteria;
import com.yeoljeong.tripmate.usersetting.application.dto.result.UserSettingResult;
import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.repository.UserSettingRepository;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserSettingQueryService {

	private final UserSettingRepository userSettingRepository;

	public UserSettingResult getOne(UUID userId) {
		UserSetting setting = userSettingRepository.findByUserIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_USER_SETTING));
		return UserSettingResult.from(setting);
	}

	public List<UUID> findAllEnableMatchingUser(MatchingCandidateCriteria criteria,
		Set<UUID> nearbyUsers) {
		return userSettingRepository.findCandidateByCriteria(
				criteria.hostUserId(), criteria.preferenceGender(), criteria.allowSmoking(),
				criteria.preferenceMbtiIE(),
				criteria.preferenceMbtiSN(),
				criteria.preferenceMbtiTF(),
				criteria.preferenceMbtiPJ(),
				nearbyUsers)
			.stream()
			.map(UserSetting::getUserId)
			.toList();
	}
}

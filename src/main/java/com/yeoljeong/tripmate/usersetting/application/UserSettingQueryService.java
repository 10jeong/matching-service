package com.yeoljeong.tripmate.usersetting.application;

import static com.yeoljeong.tripmate.usersetting.domain.exception.UserSettingErrorCode.NOT_FOUND_USER_SETTING;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.usersetting.application.dto.result.UserSettingResult;
import com.yeoljeong.tripmate.usersetting.domain.entity.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.repository.UserSettingRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSettingQueryService {

	private final UserSettingRepository userSettingRepository;

	public UserSettingResult getOne(UUID userId) {
		UserSetting setting = userSettingRepository.findByUserIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_USER_SETTING));
		return UserSettingResult.from(setting);
	}
}

package com.yeoljeong.tripmate.usersetting.application;

import static com.yeoljeong.tripmate.usersetting.domain.exception.UserSettingErrorCode.NOT_FOUND_USER_SETTING;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.usersetting.application.dto.command.UserSettingCommand;
import com.yeoljeong.tripmate.usersetting.application.dto.result.UserSettingResult;
import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserSettingCommandService {

	private final UserSettingRepository userSettingRepository;

	public UserSettingResult edit(UserSettingCommand command) {
		UserSetting setting = userSettingRepository.findByUserIdAndIsDeletedFalse(
				command.userId())
			.orElseThrow(() -> new BusinessException(NOT_FOUND_USER_SETTING));
		setting.update(
			command.isSmoking(), command.ie(), command.sn(), command.tf(), command.pj()
		);
		return UserSettingResult.from(setting);
	}
}

package com.yeoljeong.tripmate.usersetting.application;

import static com.yeoljeong.tripmate.usersetting.domain.exception.UserSettingErrorCode.NOT_FOUND_USER_SETTING;
import static com.yeoljeong.tripmate.usersetting.domain.exception.UserSettingErrorCode.USER_SETTING_ALREADY_EXISTS;

import com.yeoljeong.tripmate.exception.BusinessException;
import com.yeoljeong.tripmate.usersetting.application.dto.command.CreateUserSettingCommand;
import com.yeoljeong.tripmate.usersetting.application.dto.command.UserSettingCommand;
import com.yeoljeong.tripmate.usersetting.application.dto.result.UserSettingResult;
import com.yeoljeong.tripmate.usersetting.application.usecase.CreateUserSettingUsecase;
import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.repository.UserSettingRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserSettingCommandService implements CreateUserSettingUsecase {

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

	@Override
	public void create(CreateUserSettingCommand command) {
		if (userSettingRepository.existsByUserIdAndIsDeletedFalse(command.userId())) {
			throw new BusinessException(USER_SETTING_ALREADY_EXISTS);
		}
		try {
			userSettingRepository.save(UserSetting.create(command.userId(), command.gender()));
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException(USER_SETTING_ALREADY_EXISTS);
		}
	}

	public void activateMatching(UUID userId) {
		UserSetting setting = userSettingRepository.findByUserIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_USER_SETTING));
		setting.activateMatching();
	}

	public void deactivateMatching(UUID userId) {
		UserSetting setting = userSettingRepository.findByUserIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_USER_SETTING));
		setting.deactivateMatching();
	}
}

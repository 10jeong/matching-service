package com.yeoljeong.tripmate.matching.infrastructure.feign;

import com.yeoljeong.tripmate.matching.application.dto.result.UserSettingInfo;
import com.yeoljeong.tripmate.matching.application.external.UserSettingPort;
import com.yeoljeong.tripmate.matching.infrastructure.feign.dto.UserSettingResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSettingPortAdapter implements UserSettingPort {

	private final UserSettingClient userSettingClient;

	@Override
	public UserSettingInfo getUserSetting(UUID userId) {
		UserSettingResponse response = userSettingClient.getMySetting(userId);
		return new UserSettingInfo(
			response.userId(), response.gender(), response.isSmoking(),
			response.mbtiIE(), response.mbtiSN(), response.mbtiTF(), response.mbtiPJ()
		);
	}
}

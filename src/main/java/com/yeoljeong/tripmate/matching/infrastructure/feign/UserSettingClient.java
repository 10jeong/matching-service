package com.yeoljeong.tripmate.matching.infrastructure.feign;

import com.yeoljeong.tripmate.matching.infrastructure.feign.dto.UserSettingResponse;
import com.yeoljeong.tripmate.usersetting.application.UserSettingQueryService;
import com.yeoljeong.tripmate.usersetting.application.dto.result.UserSettingResult;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSettingClient {

	private final UserSettingQueryService userSettingQueryService;

	public UserSettingResponse getMySetting(UUID userId) {
		UserSettingResult result = userSettingQueryService.getOne(userId);
		return new UserSettingResponse(
			result.userId(),
			result.gender().name(),
			result.isSmoking(),
			result.mbtiIE().name(),
			result.mbtiSN().name(),
			result.mbtiTF().name(),
			result.mbtiPJ().name()
		);
	}
}

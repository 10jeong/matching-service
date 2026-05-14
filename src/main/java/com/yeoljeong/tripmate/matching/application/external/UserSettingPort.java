package com.yeoljeong.tripmate.matching.application.external;

import com.yeoljeong.tripmate.matching.application.dto.result.UserSettingInfo;
import java.util.UUID;

public interface UserSettingPort {
	UserSettingInfo getUserSetting(UUID userId);
}

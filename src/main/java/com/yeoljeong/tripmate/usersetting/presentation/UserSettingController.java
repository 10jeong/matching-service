package com.yeoljeong.tripmate.usersetting.presentation;

import com.yeoljeong.tripmate.response.ApiResponse;
import com.yeoljeong.tripmate.usersetting.application.UserSettingCommandService;
import com.yeoljeong.tripmate.usersetting.presentation.request.UserSettingUpdateRequest;
import com.yeoljeong.tripmate.usersetting.presentation.response.UserSettingResponse;
import com.yeoljeong.tripmate.usersetting.presentation.response.constants.UserSettingSuccessCode;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-settings")
public class UserSettingController {

	private final UserSettingCommandService userSettingCommandService;

	@PutMapping
	public ResponseEntity<ApiResponse<UserSettingResponse>> edit(
		@RequestHeader("X-User-Id") UUID userId,
		@Valid @RequestBody UserSettingUpdateRequest request
	) {
		return ResponseEntity.status(UserSettingSuccessCode.OK.getStatus()).body(
			ApiResponse.success(UserSettingSuccessCode.OK, UserSettingResponse.from(
				userSettingCommandService.edit(request.toCommand(userId)))));
	}
}

package com.yeoljeong.tripmate.usersetting.presentation;

import static com.yeoljeong.tripmate.response.constants.CommonSuccessCode.OK;
import static com.yeoljeong.tripmate.usersetting.presentation.response.constants.UserSettingSuccessCode.EDIT_SUCCESS;

import com.yeoljeong.tripmate.response.ApiResponse;
import com.yeoljeong.tripmate.usersetting.application.UserSettingCommandService;
import com.yeoljeong.tripmate.usersetting.application.UserSettingQueryService;
import com.yeoljeong.tripmate.usersetting.presentation.request.UserSettingUpdateRequest;
import com.yeoljeong.tripmate.usersetting.presentation.response.UserSettingResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	private final UserSettingQueryService userSettingQueryService;

	@PutMapping
	public ResponseEntity<ApiResponse<UserSettingResponse>> edit(
		@RequestHeader("X-User-Id") UUID userId,
		@Valid @RequestBody UserSettingUpdateRequest request
	) {
		return ResponseEntity.status(EDIT_SUCCESS.getStatus()).body(
			ApiResponse.success(EDIT_SUCCESS, UserSettingResponse.from(
				userSettingCommandService.edit(request.toCommand(userId))
			))
		);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<UserSettingResponse>> getMySetting(
		@RequestHeader("X-User-Id") UUID userId
	) {
		return ResponseEntity.status(EDIT_SUCCESS.getStatus()).body(
			ApiResponse.success(OK, UserSettingResponse.from(
				userSettingQueryService.getOne(userId)
			))
		);
	}
}

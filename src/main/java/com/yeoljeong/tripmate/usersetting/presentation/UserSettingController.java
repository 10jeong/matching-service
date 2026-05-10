package com.yeoljeong.tripmate.usersetting.presentation;

import static com.yeoljeong.tripmate.response.constants.CommonSuccessCode.OK;
import static com.yeoljeong.tripmate.usersetting.presentation.dto.response.constants.UserSettingSuccessCode.ACTIVATE_MATCHING;
import static com.yeoljeong.tripmate.usersetting.presentation.dto.response.constants.UserSettingSuccessCode.DEACTIVATE_MATCHING;
import static com.yeoljeong.tripmate.usersetting.presentation.dto.response.constants.UserSettingSuccessCode.EDIT_SUCCESS;

import com.yeoljeong.tripmate.auth.annotation.LoginUser;
import com.yeoljeong.tripmate.auth.context.UserContext;
import com.yeoljeong.tripmate.response.ApiResponse;
import com.yeoljeong.tripmate.usersetting.application.UserSettingCommandService;
import com.yeoljeong.tripmate.usersetting.application.UserSettingQueryService;
import com.yeoljeong.tripmate.usersetting.presentation.dto.request.UserSettingUpdateRequest;
import com.yeoljeong.tripmate.usersetting.presentation.dto.response.UserSettingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		@LoginUser UserContext userContext,
		@Valid @RequestBody UserSettingUpdateRequest request
	) {
		return ResponseEntity.status(EDIT_SUCCESS.getStatus()).body(
			ApiResponse.success(EDIT_SUCCESS, UserSettingResponse.from(
				userSettingCommandService.edit(request.toCommand(userContext.userId()))
			))
		);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<UserSettingResponse>> getMySetting(
		@LoginUser UserContext userContext
	) {
		return ResponseEntity.status(OK.getStatus()).body(
			ApiResponse.success(OK, UserSettingResponse.from(
				userSettingQueryService.getOne(userContext.userId())
			))
		);
	}

	@PatchMapping("/activation")
	public ResponseEntity<ApiResponse<Void>> activateMatching(
		@LoginUser UserContext userContext
	) {
		userSettingCommandService.activateMatching(userContext.userId());
		return ResponseEntity.status(EDIT_SUCCESS.getStatus()).body(
			ApiResponse.success(ACTIVATE_MATCHING, null)
		);
	}

	@PatchMapping("/deactivation")
	public ResponseEntity<ApiResponse<Void>> deactivateMatching(
		@LoginUser UserContext userContext
	) {
		userSettingCommandService.deactivateMatching(userContext.userId());
		return ResponseEntity.status(EDIT_SUCCESS.getStatus()).body(
			ApiResponse.success(DEACTIVATE_MATCHING, null)
		);
	}
}

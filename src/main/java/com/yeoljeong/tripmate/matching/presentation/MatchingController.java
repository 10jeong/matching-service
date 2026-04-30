package com.yeoljeong.tripmate.matching.presentation;

import static com.yeoljeong.tripmate.response.constants.CommonSuccessCode.CREATE;

import com.yeoljeong.tripmate.auth.LoginUser;
import com.yeoljeong.tripmate.auth.UserContext;
import com.yeoljeong.tripmate.matching.application.MatchingCommandService;
import com.yeoljeong.tripmate.matching.presentation.dto.request.CreateMatchingRequest;
import com.yeoljeong.tripmate.matching.presentation.dto.response.MatchingDetailResponse;
import com.yeoljeong.tripmate.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {

	private final MatchingCommandService commandService;

	@PostMapping
	public ResponseEntity<ApiResponse<MatchingDetailResponse>> create(
		@LoginUser UserContext userContext,
		@Valid @RequestBody CreateMatchingRequest request
	) {
		return ResponseEntity.status(CREATE.getStatus()).body(
			ApiResponse.success(CREATE, MatchingDetailResponse.from(
				commandService.create(request.toCommand(UUID.fromString(userContext.userId())))
			))
		);
	}
}

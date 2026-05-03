package com.yeoljeong.tripmate.matching.presentation;

import static com.yeoljeong.tripmate.response.constants.CommonSuccessCode.CREATE;

import com.yeoljeong.tripmate.auth.annotation.LoginUser;
import com.yeoljeong.tripmate.auth.context.UserContext;
import com.yeoljeong.tripmate.matching.application.MatchingCommandService;
import com.yeoljeong.tripmate.matching.application.external.MatchingSseManager;
import com.yeoljeong.tripmate.matching.presentation.dto.request.CreateMatchingRequest;
import com.yeoljeong.tripmate.matching.presentation.dto.response.MatchingDetailResponse;
import com.yeoljeong.tripmate.response.ApiResponse;
import jakarta.validation.Valid;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {

	private final MatchingCommandService commandService;
	private final MatchingSseManager sseManager;

	@PostMapping
	public ResponseEntity<ApiResponse<MatchingDetailResponse>> create(
		@LoginUser UserContext userContext,
		@Valid @RequestBody CreateMatchingRequest request
	) throws NoSuchAlgorithmException {
		return ResponseEntity.status(CREATE.getStatus()).body(
			ApiResponse.success(CREATE, MatchingDetailResponse.from(
				commandService.create(request.toCommand(userContext.userId()))
			))
		);
	}

	@GetMapping(value = "/sub", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe(@LoginUser UserContext userContext) {
		return sseManager.subscribe(userContext.userId());
	}
}

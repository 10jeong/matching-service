package com.yeoljeong.tripmate.matching.presentation;

import static com.yeoljeong.tripmate.response.constants.CommonSuccessCode.CREATE;
import static com.yeoljeong.tripmate.response.constants.CommonSuccessCode.OK;

import com.yeoljeong.tripmate.auth.annotation.LoginUser;
import com.yeoljeong.tripmate.auth.context.UserContext;
import com.yeoljeong.tripmate.matching.application.usecase.MatchingAcceptUsecase;
import com.yeoljeong.tripmate.matching.application.usecase.MatchingCreateUsecase;
import com.yeoljeong.tripmate.matching.application.usecase.SubscriptionUsecase;
import com.yeoljeong.tripmate.matching.presentation.dto.request.CreateMatchingRequest;
import com.yeoljeong.tripmate.matching.presentation.dto.request.UserMatchingCriteriaRequest;
import com.yeoljeong.tripmate.matching.presentation.dto.response.MatchingDetailResponse;
import com.yeoljeong.tripmate.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {

	private final MatchingAcceptUsecase acceptUsecase;
	private final MatchingCreateUsecase createUsecase;
	private final SubscriptionUsecase subscriptionUsecase;

	@PostMapping
	public ResponseEntity<ApiResponse<MatchingDetailResponse>> create(
		@LoginUser UserContext userContext,
		@Valid @RequestBody CreateMatchingRequest request
	){
		return ResponseEntity.status(CREATE.getStatus()).body(
			ApiResponse.success(CREATE, MatchingDetailResponse.from(
				createUsecase.create(request.toCommand(userContext.userId()))
			))
		);
	}

	@GetMapping(value = "/mate/sub", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe(
		@LoginUser UserContext userContext,
		@Valid @ModelAttribute UserMatchingCriteriaRequest request
	) {
		return subscriptionUsecase.
			execute(request.toCommand(userContext.userId()));
	}

	@GetMapping(value = "/host/sub", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe(
		@LoginUser UserContext userContext
	) {
		return subscriptionUsecase.
			execute(userContext.userId());
	}

	@PatchMapping("/{matchingId}/approval")
	public ResponseEntity<ApiResponse<Void>> accept(
		@LoginUser UserContext userContext,
		@PathVariable UUID matchingId
	) {
		acceptUsecase.accept(userContext.userId(), matchingId);
		return ResponseEntity.ok(ApiResponse.success(OK, null));
	}
}

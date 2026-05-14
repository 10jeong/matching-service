package com.yeoljeong.tripmate.matching.presentation.ineternal;

import com.yeoljeong.tripmate.matching.application.MatchingQueryService;
import jakarta.ws.rs.PathParam;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/internal")
@RequiredArgsConstructor
public class MatchingInternalController {

	private final MatchingQueryService queryService;

	@GetMapping("/matching/withdawal-check")
	public boolean withdrawCheck(
		@PathParam("userId") UUID userId
	) {
		return queryService.withdrawCheck(userId);
	}

}

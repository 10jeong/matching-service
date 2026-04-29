package com.yeoljeong.tripmate.matching.domain.exception;

import com.yeoljeong.tripmate.exception.constants.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MatchingErrorCode implements ErrorCode {
	MATCHING_ALREADY_IN_PROGRESS(HttpStatus.CONFLICT, "이미 진행중인 매칭이 있습니다."),
	RECRUITMENT_DEADLINE_AFTER_ACTIVITY_DATE(HttpStatus.BAD_REQUEST, "모집 마감일이 활동 예정읿 보다 늦습니다"),
	;
	private final HttpStatus status;
	private final String message;

	@Override
	public int getCode() {
		return this.status.value();
	}
}

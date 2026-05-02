package com.yeoljeong.tripmate.matching.domain.exception;

import com.yeoljeong.tripmate.exception.constants.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MatchingErrorCode implements ErrorCode {
	MATCHING_ALREADY_IN_PROGRESS(HttpStatus.CONFLICT, "이미 진행중인 매칭이 있습니다."),
	RECRUITMENT_DEADLINE_AFTER_ACTIVITY_DATE(HttpStatus.BAD_REQUEST, "모집 마감일이 활동 예정일보다 늦습니다"),
	LOCATION_REQUIRED(HttpStatus.BAD_REQUEST, "위도와 경도는 모두 입력해야 합니다."),
	INVALID_LATITUDE_RANGE(HttpStatus.BAD_REQUEST, "위도(lat)는 -90 이상 90 이하의 값이어야 합니다."),
	INVALID_LONGITUDE_RANGE(HttpStatus.BAD_REQUEST, "경도(lng)는 -180 이상 180 이하의 값이어야 합니다."),
	NO_ACTIVE_MATCHING(HttpStatus.NOT_FOUND, "진행 중인 매칭정보가 없습니다."),
	;
	private final HttpStatus status;
	private final String message;

	@Override
	public int getCode() {
		return this.status.value();
	}
}

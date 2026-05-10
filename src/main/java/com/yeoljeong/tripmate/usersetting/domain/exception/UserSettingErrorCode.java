package com.yeoljeong.tripmate.usersetting.domain.exception;

import com.yeoljeong.tripmate.exception.constants.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserSettingErrorCode implements ErrorCode {
	NOT_FOUND_USER_SETTING(HttpStatus.NOT_FOUND, "해당 유저의 세팅 정보를 찾을 수 없습니다."),
	USER_SETTING_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 생성된 설정이 존재합니다."),
	USER_ID_MISSING(HttpStatus.BAD_REQUEST, "UserId가 없어 생성할 수 없습니다."),
	GENDER_MISSING(HttpStatus.BAD_REQUEST, "Gender가 없어 생성할 수 없습니다."),
	;
	private final HttpStatus status;
	private final String message;
	@Override
	public int getCode() {
		return this.status.value();
	}
}

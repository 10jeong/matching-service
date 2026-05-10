package com.yeoljeong.tripmate.usersetting.presentation.dto.response.constants;

import com.yeoljeong.tripmate.response.constants.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserSettingSuccessCode implements SuccessCode {
	EDIT_SUCCESS(HttpStatus.OK, "세팅이 수정되었습니다."),
	ACTIVATE_MATCHING(HttpStatus.OK, "매칭 활성화 성공"),
	DEACTIVATE_MATCHING(HttpStatus.OK, "매칭 비활성화 성공"),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public int getCode() {
		return this.status.value();
	}
}

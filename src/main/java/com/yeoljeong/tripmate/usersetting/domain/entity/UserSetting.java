package com.yeoljeong.tripmate.usersetting.domain.entity;

import com.yeoljeong.tripmate.usersetting.domain.entity.constants.Gender;
import com.yeoljeong.tripmate.usersetting.domain.entity.vo.MbtiInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_setting")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSetting {

	@Id
	@Column(name = "user_id")
	private UUID userId;

	@Column(nullable = false)
	private boolean matchingEnabled;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;

	@Column(nullable = false)
	private boolean isSmoking;

	@Embedded
	private MbtiInfo mbtiInfo;

	public UserSetting create(
		UUID userId,
		boolean matchingEnabled,
		Gender gender,
		boolean isSmoking,
		MbtiInfo mbtiOption
	) {
		UserSetting userSetting = new UserSetting();
		userSetting.userId = userId;
		userSetting.matchingEnabled = matchingEnabled;
		userSetting.gender = gender;
		userSetting.isSmoking = isSmoking;
		userSetting.mbtiInfo = mbtiOption;
		return userSetting;
	}
}

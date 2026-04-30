package com.yeoljeong.tripmate.usersetting.domain.model;

import com.yeoljeong.tripmate.domain.BaseAuditEntity;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.Gender;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiTF;
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
public class UserSetting extends BaseAuditEntity {

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
	private Mbti mbti;

	public void activateMatching() {
		this.matchingEnabled = true;
	}

	public void deactivateMatching() {
		this.matchingEnabled = false;
	}

	public void update(
		boolean isSmoking,
		String ie,
		String sn,
		String tf,
		String pj
	) {
		this.isSmoking = isSmoking;
		this.mbti.update(ie, sn, tf, pj);
	}

	public static UserSetting create(UUID userId, String gender) {
		UserSetting setting = new UserSetting();
		setting.userId = userId;
		setting.gender = Gender.valueOf(gender);
		setting.matchingEnabled = false;
		setting.isSmoking = false;
		setting.mbti = new Mbti();
		return setting;
	}

	public MbtiIE getIe() {return this.getMbti().getMbtiIE();}

	public MbtiSN getSn() {return this.getMbti().getMbtiSN();}

	public MbtiTF getTf() {return this.getMbti().getMbtiTF();}

	public MbtiPJ getPj() {return this.getMbti().getMbtiPJ();}
}

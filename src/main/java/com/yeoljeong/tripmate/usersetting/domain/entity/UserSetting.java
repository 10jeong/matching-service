package com.yeoljeong.tripmate.usersetting.domain.entity;

import com.yeoljeong.tripmate.domain.BaseAuditEntity;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.Gender;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiTF;
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

	public MbtiIE getIe() {return this.getMbti().getMbtiIE();}

	public MbtiSN getSn() {return this.getMbti().getMbtiSN();}

	public MbtiTF getTf() {return this.getMbti().getMbtiTF();}

	public MbtiPJ getPj() {return this.getMbti().getMbtiPJ();}
}

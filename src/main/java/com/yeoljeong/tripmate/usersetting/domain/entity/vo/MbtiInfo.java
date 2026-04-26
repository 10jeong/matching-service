package com.yeoljeong.tripmate.usersetting.domain.entity.vo;

import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiTF;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MbtiInfo {

	private static final String UNKNOWN = "UNKNOWN";

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MbtiIE mbtiIE = MbtiIE.UNKNOWN;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MbtiSN mbtiSN = MbtiSN.UNKNOWN;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MbtiTF mbtiTF = MbtiTF.UNKNOWN;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MbtiPJ mbtiPJ = MbtiPJ.UNKNOWN;

	public boolean matches(MbtiInfo target) {
		if (target == null) return false;
		return matchAxis(this.mbtiIE, target.mbtiIE)
			&& matchAxis(this.mbtiSN, target.mbtiSN)
			&& matchAxis(this.mbtiTF, target.mbtiTF)
			&& matchAxis(this.mbtiPJ, target.mbtiPJ);
	}

	private <T extends Enum<T>> boolean matchAxis(T preference, T target) {
		if (target.name().equals(UNKNOWN)) return true;
		return preference == target;
	}
}

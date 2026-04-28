package com.yeoljeong.tripmate.usersetting.domain.model;

import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiTF;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Mbti {

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

	protected void update(String ie, String sn, String tf, String pj) {
		this.mbtiIE = ie == null ? this.mbtiIE : MbtiIE.valueOf(ie);
		this.mbtiSN = sn == null ? this.mbtiSN : MbtiSN.valueOf(sn);
		this.mbtiTF = tf == null ? this.mbtiTF : MbtiTF.valueOf(tf);
		this.mbtiPJ = pj == null ? this.mbtiPJ : MbtiPJ.valueOf(pj);
	}

	protected boolean matches(Mbti target) {
		if (target == null) return false;
		return matchAxis(this.mbtiIE, target.mbtiIE)
			&& matchAxis(this.mbtiSN, target.mbtiSN)
			&& matchAxis(this.mbtiTF, target.mbtiTF)
			&& matchAxis(this.mbtiPJ, target.mbtiPJ);
	}

	private <T extends Enum<T>> boolean matchAxis(T preference, T target) {
		if (preference.name().equals(UNKNOWN)) return true;
		return preference == target;
	}
}

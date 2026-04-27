package com.yeoljeong.tripmate.usersetting.domain.entity;

import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.entity.constants.MbtiTF;
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

	protected static Mbti of(MbtiIE ie, MbtiSN sn, MbtiTF tf, MbtiPJ pj) {
		Mbti mbti = new Mbti();
		mbti.mbtiIE = ie == null ? MbtiIE.UNKNOWN : ie;
		mbti.mbtiSN = sn == null ? MbtiSN.UNKNOWN : sn;
		mbti.mbtiTF = tf == null ? MbtiTF.UNKNOWN : tf;
		mbti.mbtiPJ = pj == null ? MbtiPJ.UNKNOWN : pj;
		return mbti;
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

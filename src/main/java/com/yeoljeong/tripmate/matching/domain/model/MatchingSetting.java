package com.yeoljeong.tripmate.matching.domain.model;

import com.yeoljeong.tripmate.matching.domain.constants.PreferenceGender;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiIE;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiPJ;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiSN;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiTF;
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
public class MatchingSetting {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PreferenceMbtiIE preferenceMbtiIE;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PreferenceMbtiSN preferenceMbtiSN;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PreferenceMbtiTF preferenceMbtiTF;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PreferenceMbtiPJ preferenceMbtiPJ;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PreferenceGender preferenceGender;

	@Column(nullable = false)
	private boolean allowSmoking;

	public static MatchingSetting of (
		PreferenceMbtiIE ie,
		PreferenceMbtiSN sn,
		PreferenceMbtiTF tf,
		PreferenceMbtiPJ pj,
		PreferenceGender gender,
		Boolean allowSmoking
	) {
		MatchingSetting setting = new MatchingSetting();
		setting.preferenceMbtiIE = ie == null ? PreferenceMbtiIE.BOTH : ie;
		setting.preferenceMbtiSN = sn == null ? PreferenceMbtiSN.BOTH : sn;
		setting.preferenceMbtiTF = tf == null ? PreferenceMbtiTF.BOTH : tf;
		setting.preferenceMbtiPJ = pj == null ? PreferenceMbtiPJ.BOTH : pj;
		setting.preferenceGender = gender == null ? PreferenceGender.BOTH : gender;
		setting.allowSmoking = allowSmoking == null || allowSmoking;
		return setting;
	}
}

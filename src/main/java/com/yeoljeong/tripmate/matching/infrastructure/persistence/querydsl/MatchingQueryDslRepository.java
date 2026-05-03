package com.yeoljeong.tripmate.matching.infrastructure.persistence.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yeoljeong.tripmate.matching.domain.constants.MatchingStatus;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceGender;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiIE;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiPJ;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiSN;
import com.yeoljeong.tripmate.matching.domain.constants.PreferenceMbtiTF;
import com.yeoljeong.tripmate.matching.domain.model.Matching;
import com.yeoljeong.tripmate.matching.domain.model.QMatching;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchingQueryDslRepository {

	private final JPAQueryFactory jpaQueryFactory;
	private final QMatching matching = QMatching.matching;

	public List<Matching> findAllByCriteria(UUID userId, String gender, boolean isSmoking,
		String mbtiIE, String mbtiSN, String mbtiTF, String mbtiPJ) {
		return jpaQueryFactory.selectFrom(matching)
			.where(
				matching.status.eq(MatchingStatus.OPEN),
				matching.hostUserId.ne(userId),
				smokingCondition(isSmoking),
				genderCondition(gender),
				mbtiCondition(mbtiIE, mbtiSN, mbtiTF, mbtiPJ)
			)
			.fetch();
	}

	private BooleanExpression smokingCondition(boolean isSmoking) {
		return matching.matchingSetting.allowSmoking.eq(isSmoking);
	}

	private BooleanExpression genderCondition(String gender) {
		if (gender == null) return null;
		return matching.matchingSetting.preferenceGender.eq(PreferenceGender.BOTH)
			.or(matching.matchingSetting.preferenceGender.eq(
				PreferenceGender.valueOf(gender.trim().toUpperCase(Locale.ROOT)))
			);
	}
	private BooleanExpression mbtiCondition(String mbtiIE, String mbtiSN, String mbtiTF, String mbtiPJ) {
		BooleanExpression condition = null;

		if (mbtiIE != null) {
			condition = mbtiIE.equalsIgnoreCase("UNKNOWN")
				? matching.matchingSetting.preferenceMbtiIE.eq(PreferenceMbtiIE.BOTH)
				: matching.matchingSetting.preferenceMbtiIE.eq(PreferenceMbtiIE.BOTH)
					.or(matching.matchingSetting.preferenceMbtiIE.eq(
						PreferenceMbtiIE.valueOf(mbtiIE.trim().toUpperCase(Locale.ROOT)))
					);
		}

		if (mbtiSN != null) {
			BooleanExpression snCondition = mbtiSN.equalsIgnoreCase("UNKNOWN")
				? matching.matchingSetting.preferenceMbtiSN.eq(PreferenceMbtiSN.BOTH)
				: matching.matchingSetting.preferenceMbtiSN.eq(PreferenceMbtiSN.BOTH)
					.or(matching.matchingSetting.preferenceMbtiSN.eq(
						PreferenceMbtiSN.valueOf(mbtiSN.trim().toUpperCase(Locale.ROOT)))
					);
			condition = condition == null ? snCondition : condition.and(snCondition);
		}

		if (mbtiTF != null) {
			BooleanExpression tfCondition = mbtiTF.equalsIgnoreCase("UNKNOWN")
				? matching.matchingSetting.preferenceMbtiTF.eq(PreferenceMbtiTF.BOTH)
				: matching.matchingSetting.preferenceMbtiTF.eq(PreferenceMbtiTF.BOTH)
					.or(matching.matchingSetting.preferenceMbtiTF.eq(
						PreferenceMbtiTF.valueOf(mbtiTF.trim().toUpperCase(Locale.ROOT)))
					);
			condition = condition == null ? tfCondition : condition.and(tfCondition);
		}

		if (mbtiPJ != null) {
			BooleanExpression pjCondition = mbtiPJ.equalsIgnoreCase("UNKNOWN")
				? matching.matchingSetting.preferenceMbtiPJ.eq(PreferenceMbtiPJ.BOTH)
				: matching.matchingSetting.preferenceMbtiPJ.eq(PreferenceMbtiPJ.BOTH)
					.or(matching.matchingSetting.preferenceMbtiPJ.eq(
						PreferenceMbtiPJ.valueOf(mbtiPJ.trim().toUpperCase(Locale.ROOT)))
					);
			condition = condition == null ? pjCondition : condition.and(pjCondition);
		}

		return condition;
	}
}

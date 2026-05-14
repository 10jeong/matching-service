package com.yeoljeong.tripmate.usersetting.infrastructure.persistence.querydsl;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yeoljeong.tripmate.usersetting.domain.model.QUserSetting;
import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.Gender;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiIE;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiPJ;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiSN;
import com.yeoljeong.tripmate.usersetting.domain.model.constants.MbtiTF;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserSettingQueryDslRepository {

	private final JPAQueryFactory jpaQueryFactory;
	private final QUserSetting userSetting = QUserSetting.userSetting;

	public List<UserSetting> findCandidatesByCriteria(UUID hostUserId, String preferenceGender, boolean allowSmoking,
		String preferenceMbtiIE, String preferenceMbtiSN, String preferenceMbtiTF, String preferenceMbtiPJ, Set<UUID> nearbyUsers
	) {
		return jpaQueryFactory.selectFrom(userSetting)
			.where(
				userSetting.matchingEnabled.isTrue(),
				userSetting.isDeleted.isFalse(),
				userSetting.userId.ne(hostUserId),
				userSetting.userId.in(nearbyUsers),
				smokingCondition(allowSmoking),
				genderCondition(preferenceGender),
				mbtiCondition(
					preferenceMbtiIE, preferenceMbtiSN, preferenceMbtiTF, preferenceMbtiPJ
				))
			.fetch();
	}

	private BooleanExpression smokingCondition(boolean allowSmoking) {
		if (allowSmoking) return null; // 흡연 허용이면 모두 가능
		return userSetting.isSmoking.isFalse();
	}

	private BooleanExpression genderCondition(String preferenceGender) {
		if (preferenceGender == null || "BOTH".equals(preferenceGender)) return null;
		return userSetting.gender.eq(Gender.valueOf(preferenceGender));
	}

	private BooleanExpression mbtiCondition(String ie, String sn, String tf, String pj) {
		BooleanExpression condition = null;

		if (ie != null && !"BOTH".equals(ie) && !"UNKNOWN".equals(ie)) {
			condition = userSetting.mbti.mbtiIE.eq(MbtiIE.valueOf(ie));
		}

		if (sn != null && !"BOTH".equals(sn) && !"UNKNOWN".equals(sn)) {
			BooleanExpression snCondition = userSetting.mbti.mbtiSN.eq(MbtiSN.valueOf(sn));
			condition = condition == null ? snCondition : condition.and(snCondition);
		}

		if (tf != null && !"BOTH".equals(tf) && !"UNKNOWN".equals(tf)) {
			BooleanExpression tfCondition = userSetting.mbti.mbtiTF.eq(MbtiTF.valueOf(tf));
			condition = condition == null ? tfCondition : condition.and(tfCondition);
		}

		if (pj != null && !"BOTH".equals(pj) && !"UNKNOWN".equals(pj)) {
			BooleanExpression pjCondition = userSetting.mbti.mbtiPJ.eq(MbtiPJ.valueOf(pj));
			condition = condition == null ? pjCondition : condition.and(pjCondition);
		}

		return condition;
	}
}

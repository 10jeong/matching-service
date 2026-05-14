package com.yeoljeong.tripmate.usersetting.infrastructure.persistence.repositoryimpl;

import com.yeoljeong.tripmate.usersetting.domain.model.UserSetting;
import com.yeoljeong.tripmate.usersetting.domain.repository.UserSettingRepository;
import com.yeoljeong.tripmate.usersetting.infrastructure.persistence.jpa.UserSettingJpaRepository;
import com.yeoljeong.tripmate.usersetting.infrastructure.persistence.querydsl.UserSettingQueryDslRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserSettingRepositoryImpl implements UserSettingRepository {

	private final UserSettingJpaRepository userSettingRepository;
	private final UserSettingQueryDslRepository userSettingQueryDslRepository;

	@Override
	public Optional<UserSetting> findByUserIdAndIsDeletedFalse(UUID userId) {
		return userSettingRepository.findByUserIdAndIsDeletedFalse(userId);
	}

	@Override
	public boolean existsByUserIdAndIsDeletedFalse(UUID userId) {
		return userSettingRepository.existsByUserIdAndIsDeletedFalse(userId);
	}

	@Override
	public UserSetting save(UserSetting userSetting) {
		return userSettingRepository.save(userSetting);
	}

	@Override
	public List<UserSetting> findCandidateByCriteria(UUID hostUserId, String gender,
		boolean allowSmoking, String mbtiIE, String mbtiSN, String mbtiTF, String mbtiPJ, Set<UUID> nearbyUsers) {
		return userSettingQueryDslRepository.findCandidatesByCriteria(
			hostUserId, gender, allowSmoking, mbtiIE, mbtiSN, mbtiTF, mbtiPJ, nearbyUsers
		);
	}
}

